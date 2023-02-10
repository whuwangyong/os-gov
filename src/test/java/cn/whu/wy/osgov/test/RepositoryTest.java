package cn.whu.wy.osgov.test;

import cn.whu.wy.osgov.entity.*;
import cn.whu.wy.osgov.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 18:41
 */
// @Sql(scripts = {"/ddl-mysql.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
// 如果注解写在类上面，每个test方法之前都会执行一次脚本
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class RepositoryTest {

    @Autowired
    private DataSource dataSource;

    AppRepository appRepository;
    ArtifactRepository artifactRepository;
    HostRepository hostRepository;
    LicenseRepository licenseRepository;
    TagRepository tagRepository;
    VulnerabilityRepository vulnerabilityRepository;

    AppHostRepository appHostRepository;
    ArtifactAppRepository artifactAppRepository;
    ArtifactLicenseRepository artifactLicenseRepository;
    ArtifactTagRepository artifactTagRepository;
    ArtifactVulnerabilityRepository artifactVulnerabilityRepository;

    Random random = new Random(47);

    final int appNum = 308;
    final int artifactNum = 1234;
    final int hostNum = 251;
    final int licenseNum = 116;
    final int tagNum = 69;
    final int vulnerabilityNum = 745;

    @Autowired
    public RepositoryTest(AppRepository appRepository, ArtifactRepository artifactRepository,
                          HostRepository hostRepository, LicenseRepository licenseRepository,
                          TagRepository tagRepository, VulnerabilityRepository vulnerabilityRepository,
                          AppHostRepository appHostRepository,
                          ArtifactAppRepository artifactAppRepository,
                          ArtifactLicenseRepository artifactLicenseRepository,
                          ArtifactTagRepository artifactTagRepository,
                          ArtifactVulnerabilityRepository artifactVulnerabilityRepository) {
        this.appRepository = appRepository;
        this.artifactRepository = artifactRepository;
        this.hostRepository = hostRepository;
        this.licenseRepository = licenseRepository;
        this.tagRepository = tagRepository;
        this.vulnerabilityRepository = vulnerabilityRepository;
        this.appHostRepository = appHostRepository;
        this.artifactAppRepository = artifactAppRepository;
        this.artifactLicenseRepository = artifactLicenseRepository;
        this.artifactTagRepository = artifactTagRepository;
        this.artifactVulnerabilityRepository = artifactVulnerabilityRepository;
    }

    @Sql(scripts = {"/ddl-mysql.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Test
    @Order(-1)
    void createTableUseAnno() {
        log.info("createTableUseAnno");
    }

    @Test
    @Order(0)
    void createTable() throws SQLException {
        log.info("createTable");
        Resource classPathResource = new ClassPathResource("ddl-mysql.sql");
        EncodedResource encodedResource = new EncodedResource(classPathResource, "utf-8");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
    }

    // 使用单挑
    @Test
    @Order(1)
    public void insertApp() {
        log.info("insertApp");
        String[] envs = {"生产", "测试"};
        List<App> appList = new ArrayList<>(appNum);
        for (int i = 1; i <= appNum; i++) {
            appList.add(new App("测试应用" + i, envs[random.nextInt(envs.length)], "核心"));
        }
        appRepository.add(appList);
    }

    @Test
    @Order(2)
    public void insertArtifact() {
        log.info("insertArtifact");
        String[] authors = {Artifact.Author.Independent.getValue(), Artifact.Author.OpenSource.getValue(), Artifact.Author.Commercial.getValue(), Artifact.Author.XinChuang.getValue()};
        String[] roles = {Artifact.Role.Component.getValue(), Artifact.Role.Software.getValue(), Artifact.Role.Tool.getValue(), Artifact.Role.NA.getValue()};
        List<Artifact> artifactList = new ArrayList<>(artifactNum);
        for (int i = 1; i <= artifactNum; i++) {
            artifactList.add(
                    Artifact.builder()
                            .qaxId(UUID.randomUUID().toString())
                            .org("org.test" + i % 10)
                            .name("component-" + i)
                            .version("0." + i)
                            .publishDate(LocalDate.now())
                            .author(authors[random.nextInt(authors.length)])
                            .role(roles[random.nextInt(roles.length)])
                            .env(i % 6 == 0 ? "生产库" : "开发测试库")
                            .description("一些简介")
                            .build()
            );
        }
        artifactRepository.add(artifactList);
    }

    @Test
    @Order(3)
    public void insertHost() {
        log.info("insertHost");
        String[] hardwares = {"物理机", "虚拟机", "容器"};
        String[] networks = {"生产", "办公", "开发"};
        List<Host> hostList = new ArrayList<>(hostNum);
        for (int i = 1; i <= hostNum; i++) {
            hostList.add(new Host("192.168.0." + i, hardwares[random.nextInt(hardwares.length)], networks[random.nextInt(networks.length)]));
        }
        hostRepository.add(hostList);
    }

    @Test
    @Order(4)
    public void insertLicense() {
        log.info("insertLicense");
        byte[] risks = {0, 1, 2, 3};
        List<License> licenseList = new ArrayList<>(licenseNum);
        for (int i = 1; i <= licenseNum; i++) {
            licenseList.add(new License("test-license-" + i, risks[random.nextInt(risks.length)]));

        }
        licenseRepository.add(licenseList);
    }

    @Test
    @Order(5)
    public void insertTag() {
        log.info("insertTag");
        for (int i = 1; i <= tagNum; i++) {
            tagRepository.add(new Tag("test-tag-" + i));
        }
    }

    @Test
    @Order(6)
    public void insertVulnerability() {
        log.info("insertVulnerability");
        byte[] level = {1, 2, 3, 4};
        byte[] difficulty = {1, 2, 3, 4};
        List<Vulnerability> vulnerabilityList = new ArrayList<>(vulnerabilityNum);
        for (int i = 1; i <= vulnerabilityNum; i++) {
            vulnerabilityList.add(Vulnerability.builder()
                    .cnnvd("cnnvd" + i)
                    .cve("cve" + i)
                    .qaxOssId(UUID.randomUUID().toString().substring(0,20))
                    .name("test-vul" + i)
                    .level(level[random.nextInt(level.length)])
                    .utilizeDegree(difficulty[random.nextInt(difficulty.length)])
                    .exposedDate(LocalDate.now())
                    .description("一些描述")
                    .solution("升级")
                    .build());
        }
        vulnerabilityRepository.add(vulnerabilityList);
    }

    @Test
    @Order(7)
    public void insertAppHost() {
        List<AppHost> appHosts = new ArrayList<>();
        for (int appId = 1; appId <= appNum; appId++) {
            // 将app随机部署到一个host上
            int hostId = random.nextInt(hostNum) + 1;
            appHosts.add(new AppHost(appId, hostId));
        }
        appHostRepository.add(appHosts);
    }

    @Test
    @Order(8)
    public void insertArtifactApp() {
        List<ArtifactApp> artifactApps = new ArrayList<>();
        for (int artifactId = 1; artifactId <= artifactNum; artifactId++) {
            // 将artifact随机安排到一个app里
            int appId = random.nextInt(appNum) + 1;
            artifactApps.add(new ArtifactApp(artifactId, appId, false, false));
        }
        artifactAppRepository.add(artifactApps);
    }

    @Test
    @Order(9)
    public void insertArtifactLicense() {
        List<ArtifactLicense> artifactLicenses = new ArrayList<>();
        for (int artifactId = 1; artifactId <= artifactNum; artifactId++) {
            // 给artifact随机安排一个license
            int licenseId = random.nextInt(licenseNum) + 1;
            artifactLicenses.add(new ArtifactLicense(artifactId, licenseId));
        }
        artifactLicenseRepository.add(artifactLicenses);
    }

    @Test
    @Order(10)
    public void insertArtifactTag() {
        List<ArtifactTag> artifactTags = new ArrayList<>();
        for (int artifactId = 1; artifactId <= artifactNum; artifactId++) {
            // 给artifact随机安排一个tag
            int tagId = random.nextInt(tagNum) + 1;
            artifactTags.add(new ArtifactTag(artifactId, tagId));
        }
        artifactTagRepository.add(artifactTags);
    }

    @Test
    @Order(11)
    public void insertArtifactVulnerability() {
        Set<ArtifactVulnerability> artifactVulnerabilities = new HashSet<>();
        for (int artifactId = 1; artifactId <= artifactNum; artifactId++) {
            // 被3或4整除的加1个漏洞
            if (artifactId % 3 == 0 || artifactId % 4 == 0) {
                artifactVulnerabilities.add(new ArtifactVulnerability(artifactId, random.nextInt(vulnerabilityNum) + 1));
            }
            // 被7整除再加2个漏洞
            if (artifactId % 7 == 0) {
                artifactVulnerabilities.add(new ArtifactVulnerability(artifactId, random.nextInt(vulnerabilityNum) + 1));
                artifactVulnerabilities.add(new ArtifactVulnerability(artifactId, random.nextInt(vulnerabilityNum) + 1));
            }
        }
        artifactVulnerabilityRepository.add(new ArrayList<>(artifactVulnerabilities));
    }


}
