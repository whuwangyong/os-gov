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
import java.util.Random;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 18:41
 */
@Sql(scripts = {"/ddl.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
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

    @Autowired
    public RepositoryTest(AppRepository appRepository, ArtifactRepository artifactRepository, HostRepository hostRepository, LicenseRepository licenseRepository, TagRepository tagRepository, VulnerabilityRepository vulnerabilityRepository) {
        this.appRepository = appRepository;
        this.artifactRepository = artifactRepository;
        this.hostRepository = hostRepository;
        this.licenseRepository = licenseRepository;
        this.tagRepository = tagRepository;
        this.vulnerabilityRepository = vulnerabilityRepository;
    }

    @Test
    @Order(1)
    void createTable() throws SQLException {
        Resource classPathResource = new ClassPathResource("ddl.sql");
        EncodedResource encodedResource = new EncodedResource(classPathResource, "utf-8");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
    }

    @Test
    @Order(2)
    public void insert() {
        Random random = new Random(47);

        String[] envs = {"生产", "测试"};
        for (int i = 0; i < 308; i++) {
            appRepository.add(new App("测试应用" + i, envs[random.nextInt(envs.length)]));
        }

        String[] authors = {Author.Independent.getValue(), Author.OpenSource.getValue(), Author.Commercial.getValue(), Author.XinChuang.getValue()};
        String[] roles = {Role.Component.getValue(), Role.Software.getValue(), Role.Tool.getValue(), Role.NA.getValue()};
        for (int i = 1; i <= 1234; i++) {
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

        String[] hardwares = {"物理机", "虚拟机", "容器"};
        String[] networks = {"生产", "办公", "开发"};
        for (int i = 0; i < 241; i++) {
            hostRepository.add(new Host("192.168.0." + i, hardwares[random.nextInt(hardwares.length)], networks[random.nextInt(networks.length)]));
        }

        byte[] risks = {0, 1, 2, 3};
        for (int i = 0; i < 116; i++) {
            licenseRepository.add(new License("test-license-" + i, risks[random.nextInt(risks.length)]));

        }

        for (int i = 0; i < 79; i++) {
            tagRepository.add(new Tag("test-tag-" + i));
        }

        byte[] level = {0, 1, 2, 3};
        byte[] difficulty = {0, 1, 2, 3};
        for (int i = 0; i < 745; i++) {
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
