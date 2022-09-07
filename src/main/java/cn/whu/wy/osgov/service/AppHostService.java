package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.dto.AppHostDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/05
 * Time 20:09
 */
@Service
public class AppHostService extends BaseService {


    public AppHostService(DataSource dataSource) {
        super(dataSource);
    }

    public List<AppHostDto> get() {
        String sql = "SELECT app.id   AS appId,\n" +
                "       app.name AS appName,\n" +
                "       app.env  AS appEnv,\n" +
                "       host.id  AS hostId,\n" +
                "       ip,\n" +
                "       hardware,\n" +
                "       network\n" +
                "FROM app,\n" +
                "     host,\n" +
                "     app_host\n" +
                "WHERE app.id = app_host.app_id\n" +
                "  AND host.id = app_host.host_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AppHostDto.class));
    }


    public List<AppHostDto> queryByAppName(String keyword) {
        String sql = "SELECT app.id   AS appId,\n" +
                "       app.name AS appName,\n" +
                "       app.env  AS appEnv,\n" +
                "       host.id  AS hostId,\n" +
                "       ip,\n" +
                "       hardware,\n" +
                "       network\n" +
                "FROM app,\n" +
                "     host,\n" +
                "     app_host\n" +
                "WHERE app.id = app_host.app_id\n" +
                "  AND host.id = app_host.host_id" +
                "  AND app.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AppHostDto.class));
    }

    public List<AppHostDto> queryByIp(String keyword) {
        String sql = "SELECT app.id   AS appId,\n" +
                "       app.name AS appName,\n" +
                "       app.env  AS appEnv,\n" +
                "       host.id  AS hostId,\n" +
                "       ip,\n" +
                "       hardware,\n" +
                "       network\n" +
                "FROM app,\n" +
                "     host,\n" +
                "     app_host\n" +
                "WHERE app.id = app_host.app_id\n" +
                "  AND host.id = app_host.host_id" +
                "  AND ip LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AppHostDto.class));
    }
}
