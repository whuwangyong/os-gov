package cn.whu.wy.osgov.service.qax;

import cn.whu.wy.osgov.entity.*;
import cn.whu.wy.osgov.entity.qax.ProjectTask;
import cn.whu.wy.osgov.repository.*;
import cn.whu.wy.osgov.service.qax.bean.Project;
import cn.whu.wy.osgov.service.qax.bean.QaxVulnerability;
import cn.whu.wy.osgov.service.qax.bean.TaskComponentWithMd5;
import cn.whu.wy.osgov.service.qax.response.QaxResponse;
import cn.whu.wy.osgov.service.qax.response.QaxResponsePageableData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author WangYong
 * @Date 2022/09/27
 * @Time 18:15
 */
@Service
@Slf4j
public class QaxProvider extends BaseQaxProvider implements InitializingBean {

    @Autowired
    private ArtifactRepository artifactRepository;
    @Autowired
    private QaxProjectTaskRepository qaxProjectTaskRepository;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private VulnerabilityRepository vulnerabilityRepository;
    @Autowired
    private ArtifactLicenseRepository artifactLicenseRepository;
    @Autowired
    private AppRepository appRepository;
    @Autowired
    private ArtifactAppRepository artifactAppRepository;
    @Autowired
    private ArtifactVulnerabilityRepository artifactVulnerabilityRepository;

    private final String QAX_API = "https://ip:8449/open-api/v3/";
    private final String QAX_API_PROJECT_LIST = QAX_API + "project/list";
    private final String QAX_API_COMPONENT_LIST = QAX_API + "task/componentwithmd5/list";
    private final String QAX_API_VULNERABILITY_LIST = QAX_API + "task/vulnerability/list";


    @Override
    public void afterPropertiesSet() throws Exception {
//        Thread t = new Thread(this::run);
//        t.setName("thread_QaxProvider");
//        t.start();
    }

    private void run() {
        while (true) {
            int newTasks = importProjectTasks();
            if (newTasks > 0) {
                log.info("import {} new tasks", newTasks);
            }

            importVulnerabilities();
            importComponentsAndLicenses();
            addComponentVulRelationship();
            addComponentLicenseRelationship();

            try {
                TimeUnit.HOURS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将当前的project list保存到db。每个project里有多个task
     */
    private int importProjectTasks() {
        List<ProjectTask> projectTasks = new ArrayList<>();

        List<Project> projects = get(QAX_API_PROJECT_LIST, new ParameterizedTypeReference<>() {
        });
        projects.forEach(project -> {
            project.getTaskInfos().forEach(taskInfo -> {
                ProjectTask o = ProjectTask.builder()
                        .projectId(project.getProjectId())
                        .projectName(project.getProjectName())
                        .taskId(taskInfo.getTaskId())
                        .taskName(taskInfo.getTaskName())
                        .taskBeginTime(taskInfo.getTaskBeginTime())
                        .taskEndTime(taskInfo.getTaskEndTime())
                        .build();
                projectTasks.add(o);
            });
        });
        // db里面已存在的
        List<ProjectTask> existed = qaxProjectTaskRepository.getAll();
        // 新来的
        List<ProjectTask> newcomes = projectTasks.stream().filter(o -> !existed.contains(o)).collect(Collectors.toList());
        qaxProjectTaskRepository.add(newcomes);
        return newcomes.size();
    }

    /**
     * 对未执行漏洞导入的project task，将其漏洞导入到db
     */
    private void importVulnerabilities() {
        // 找出未导入漏洞的task
        List<ProjectTask> projectTasks = qaxProjectTaskRepository.queryEqual("vulnerability_imported", "FALSE");
        // 获取该task的漏洞并存入db
        projectTasks.forEach(task -> {
            List<QaxVulnerability> vuls = post(QAX_API_VULNERABILITY_LIST, task.getTaskId(), 100, new ParameterizedTypeReference<>() {
            });
            importVulnerabilities0(vuls, task);
        });
    }

    @Transactional
    void importVulnerabilities0(List<QaxVulnerability> qaxVulnerabilityList, ProjectTask task) {
        // 将新增的漏洞入库
        List<Vulnerability> existed = vulnerabilityRepository.getAll();
        List<Vulnerability> newcomes = qaxVulnerabilityList.stream()
                .map(Vulnerability::convertFrom)
                .filter(o -> !existed.contains(o))
                .collect(Collectors.toList());
        vulnerabilityRepository.add(newcomes);
        // 更新projectTask里面的漏洞导入字段为true
        task.setVulnerabilityImported(true);
        qaxProjectTaskRepository.update(task);
    }


    /**
     * 对未执行组件导入的project task，将其组件、协议导入db
     */
    private void importComponentsAndLicenses() {
        List<ProjectTask> projectTasks = qaxProjectTaskRepository.queryEqual("component_and_license_imported", "FALSE");
        projectTasks.forEach(task -> {
            List<TaskComponentWithMd5.Component> components = post(QAX_API_COMPONENT_LIST, task.getTaskId(), 100, new ParameterizedTypeReference<>() {
            });
            importComponentsAndLicenses0(components, task);
        });
    }

    @Transactional
    void importComponentsAndLicenses0(List<TaskComponentWithMd5.Component> components, ProjectTask task) {
        // 将新增的组件入库
        List<Artifact> existedComponents = artifactRepository.getAll();
        List<Artifact> newcomeComponents = components.stream()
                .map(Artifact::convertFrom)
                .filter(Objects::nonNull)
                .distinct()
                .filter(o -> !existedComponents.contains(o))
                .collect(Collectors.toList());
        artifactRepository.add(newcomeComponents);

        // 将新增的协议入库
        List<License> existedLicenses = licenseRepository.getAll();
        List<License> newcomeLicenses = new ArrayList<>();
        components.forEach(component -> {
            List<License> licenses = component.getLicenseInfos().stream()
                    .map(License::convertFrom)
                    .filter(license -> !existedLicenses.contains(license) && !newcomeLicenses.contains(license))
                    .collect(Collectors.toList());
            newcomeLicenses.addAll(licenses);
        });
        licenseRepository.add(newcomeLicenses);

        // 更新projectTask里面的组件协议导入字段为true
        task.setComponentAndLicenseImported(true);
        qaxProjectTaskRepository.update(task);
    }


    /**
     * 对未执行组件-漏洞关系导入的project task，导入组件-漏洞关系到db
     */
    private void addComponentVulRelationship() {
        // 只有漏洞和组件已导入的任务，才能做组件-漏洞关系的导入
        Map<String, Object> params = Map.of(
                "vulnerability_imported", true,
                "component_and_license_imported", true,
                "component_vul_relationship_imported", false
        );
        List<ProjectTask> projectTasks = qaxProjectTaskRepository.queryEqual(params);
        projectTasks.forEach(task -> {
            List<TaskComponentWithMd5.Component> components = post(QAX_API_COMPONENT_LIST, task.getTaskId(), 100, new ParameterizedTypeReference<>() {
            });
            addComponentVulRelationship0(components, task);
        });
    }

    @Transactional
    void addComponentVulRelationship0(List<TaskComponentWithMd5.Component> components, ProjectTask task) {
        List<ArtifactVulnerability> existed = artifactVulnerabilityRepository.getAll();
        List<ArtifactVulnerability> newcomes = new ArrayList<>();

        components.forEach(component -> {
            try {
                Artifact artifact = artifactRepository.findByUniqueIndex(Map.of("qax_id", component.getComponentId()));
                assert artifact != null;
                component.getVulnerabilityInfos().forEach(vulInfo -> {
                    Vulnerability vulnerability = vulnerabilityRepository.findByUniqueIndex(Map.of("qax_oss_id", vulInfo.getQaxOssId()));
                    assert vulnerability != null;
                    ArtifactVulnerability av = new ArtifactVulnerability(artifact.getId(), vulnerability.getId());
                    if (!existed.contains(av) && !newcomes.contains(av)) {
                        newcomes.add(av);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        artifactVulnerabilityRepository.add(newcomes);
        task.setComponentVulRelationshipImported(true);
        qaxProjectTaskRepository.update(task);
    }

    private void addComponentLicenseRelationship() {
        // 只有组件已导入的任务，才能做组件-协议关系的导入
        Map<String, Object> params = Map.of(
                "component_and_license_imported", true,
                "component_license_relationship_imported", false
        );
        List<ProjectTask> projectTasks = qaxProjectTaskRepository.queryEqual(params);
        projectTasks.forEach(task -> {
            List<TaskComponentWithMd5.Component> components = post(QAX_API_COMPONENT_LIST, task.getTaskId(), 100, new ParameterizedTypeReference<>() {
            });
            addComponentLicenseRelationship0(components, task);
        });
    }

    @Transactional
    void addComponentLicenseRelationship0(List<TaskComponentWithMd5.Component> components, ProjectTask task) {
        List<ArtifactLicense> existed = artifactLicenseRepository.getAll();
        List<ArtifactLicense> newcomes = new ArrayList<>();
        components.forEach(component -> {
            try {
                Artifact artifact = artifactRepository.findByUniqueIndex(Map.of("qax_id", component.getComponentId()));
                assert artifact != null;
                component.getLicenseInfos().forEach(licenseInfo -> {
                    License license = licenseRepository.findByUniqueIndex(Map.of("name", licenseInfo.getLicenseName()));
                    assert license != null;
                    ArtifactLicense al = new ArtifactLicense(artifact.getId(), license.getId());
                    if (!existed.contains(al) && !newcomes.contains(al)) {
                        newcomes.add(al);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        artifactLicenseRepository.add(newcomes);
        task.setComponentLicenseRelationshipImported(true);
        qaxProjectTaskRepository.update(task);
    }


}
