package cn.whu.wy.osgov.test;

import cn.whu.wy.osgov.entity.Component;
import cn.whu.wy.osgov.repository.ComponentRepository;
import cn.whu.wy.osgov.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author WangYong
 * Date 2022/08/11
 * Time 20:13
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ComponentRepositoryTest {

    @Autowired
    ComponentRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Order(1)
    @Test
    public void init() {
        log.info("init");
        jdbcTemplate.execute("truncate component");
    }

    @Order(2)
    @Test
    public void testAdd() {
        log.info("testAdd");
        for (int i = 1; i <= 1000; i++) {
            repository.add(
                    Component.builder()
                            .groupId("org.test" + i % 10)
                            .artifactId("component-" + i)
                            .version("0.0.1")
                            .build()
            );
        }
        Integer count = repository.count();
        assert count == 1000;
    }


}
