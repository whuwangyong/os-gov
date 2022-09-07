package cn.whu.wy.osgov.service;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/09/05
 * Time 20:13
 */
public abstract class BaseService {

    protected final JdbcTemplate jdbcTemplate;

    public BaseService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

    }
}
