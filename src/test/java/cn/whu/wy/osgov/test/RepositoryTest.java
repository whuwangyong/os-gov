package cn.whu.wy.osgov.test;

import cn.whu.wy.osgov.entity.*;
import cn.whu.wy.osgov.entity.artifact.Artifact;
import cn.whu.wy.osgov.entity.artifact.Author;
import cn.whu.wy.osgov.entity.artifact.Role;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 18:41
 */
// @Sql(scripts = {"/ddl.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
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

    final int apps = 308;
    final int artifacts = 1234;
    final int hosts = 251;
    final int licenses = 116;
    final int tags = 69;
    final int vulnerabilities = 745;

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

    @Sql(scripts = {"/ddl.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
//    @Test
    @Order(-1)
    void createTableUseAnno() {
        log.info("createTableUseAnno");
    }

    @Test
    @Order(0)
    void createTable() throws SQLException {
        log.info("createTable");
        Resource classPathResource = new ClassPathResource("ddl.sql");
        EncodedResource encodedResource = new EncodedResource(classPathResource, "utf-8");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
    }

    @Test
    @Order(1)
    public void insertApp() {
        log.info("insertApp");
        String[] envs = {"生产", "测试"};
        for (int i = 0; i < apps; i++) {
            appRepository.add(new App("测试应用" + i, envs[random.nextInt(envs.length)]));
        }
    }

    @Test
    @Order(2)
    public void insertArtifact() {
        log.info("insertArtifact");
        String[] authors = {Author.Independent.getValue(), Author.OpenSource.getValue(), Author.Commercial.getValue(), Author.XinChuang.getValue()};
        String[] roles = {Role.Component.getValue(), Role.Software.getValue(), Role.Tool.getValue(), Role.NA.getValue()};
        for (int i = 1; i <= artifacts; i++) {
            artifactRepository.add(
                    Artifact.builder()
                            .org("org.test" + i % 10)
                            .name("component-" + i)
                            .version("0." + i)
                            .publishDate(LocalDate.now())
                            .author(authors[random.nextInt(authors.length)])
                            .role(roles[random.nextInt(roles.length)])
                            .description("一些简介")
                            .build()
            );
        }
    }

    @Test
    @Order(3)
    public void insertHost() {
        log.info("insertHost");
        String[] hardwares = {"物理机", "虚拟机", "容器"};
        String[] networks = {"生产", "办公", "开发"};
        for (int i = 0; i < hosts; i++) {
            hostRepository.add(new Host("192.168.0." + i, hardwares[random.nextInt(hardwares.length)], networks[random.nextInt(networks.length)]));
        }
    }

    @Test
    @Order(4)
    public void insertLicense() {
        log.info("insertLicense");
        byte[] risks = {0, 1, 2, 3};
        for (int i = 0; i < licenses; i++) {
            licenseRepository.add(new License("test-license-" + i, risks[random.nextInt(risks.length)]));

        }
    }

    @Test
    @Order(5)
    public void insertTag() {
        log.info("insertTag");
        for (int i = 0; i < tags; i++) {
            tagRepository.add(new Tag("test-tag-" + i));
        }
    }

    @Test
    @Order(6)
    public void insertVulnerability() {
        log.info("insertVulnerability");
        byte[] level = {0, 1, 2, 3};
        byte[] difficulty = {0, 1, 2, 3};
        for (int i = 0; i < vulnerabilities; i++) {
            vulnerabilityRepository.add(Vulnerability.builder()
                    .cnnvd("cnnvd" + i)
                    .cve("cve" + i)
                    .cwe("cwe" + i)
                    .name("test-vul" + i)
                    .level(level[random.nextInt(level.length)])
                    .difficulty(difficulty[random.nextInt(difficulty.length)])
                    .exposedDate(LocalDate.now())
                    .description("一些描述")
                    .suggestion("升级")
                    .build());
        }
    }


    }
}
