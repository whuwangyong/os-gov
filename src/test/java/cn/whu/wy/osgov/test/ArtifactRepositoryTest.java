package cn.whu.wy.osgov.test;

import cn.whu.wy.osgov.entity.artifact.Artifact;
import cn.whu.wy.osgov.entity.artifact.Author;
import cn.whu.wy.osgov.entity.artifact.Role;
import cn.whu.wy.osgov.repository.ArtifactRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.time.LocalDate;

/**
 * @author WangYong
 * Date 2022/08/11
 * Time 20:13
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ArtifactRepositoryTest {

    @Autowired
    ArtifactRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Sql(scripts = {"/ddl.sql"}, config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Order(1)
    @Test
    public void init() {
        log.info("init");
        jdbcTemplate.execute("USE test; DELETE FROM artifact; ALTER TABLE artifact AUTO_INCREMENT=1");
    }

    @Order(2)
    @Test
    public void testAdd() {
        log.info("testAdd");
        for (int i = 1; i <= 1000; i++) {
            repository.add(
                    Artifact.builder()
                            .org("org.test" + i % 10)
                            .name("component-" + i)
                            .version("0." + i)
                            .publishDate(LocalDate.now())
                            .author(Author.OpenSource.getValue())
                            .role(Role.Component.getValue())
                            .build()
            );
        }
        Integer count = repository.count();
        assert count == 1000;
    }


}
