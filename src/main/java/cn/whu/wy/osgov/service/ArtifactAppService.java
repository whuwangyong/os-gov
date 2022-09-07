package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.dto.ArtifactAppDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/05
 * Time 21:56
 */
@Service
public class ArtifactAppService extends BaseService {

    public ArtifactAppService(DataSource dataSource) {
        super(dataSource);
    }

    public List<ArtifactAppDto> get() {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       app.name      AS app_name,\n" +
                "       re_dev,\n" +
                "       support\n" +
                "FROM artifact,\n" +
                "     app,\n" +
                "     artifact_app\n" +
                "WHERE artifact.id = artifact_app.artifact_id\n" +
                "  AND app.id = artifact_app.app_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactAppDto.class));
    }

    public List<ArtifactAppDto> queryByAppName(String keyword) {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       app.name      AS app_name,\n" +
                "       re_dev,\n" +
                "       support\n" +
                "FROM artifact,\n" +
                "     app,\n" +
                "     artifact_app\n" +
                "WHERE artifact.id = artifact_app.artifact_id\n" +
                "  AND app.id = artifact_app.app_id" +
                "  AND app.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactAppDto.class));
    }

    public List<ArtifactAppDto> queryByArtifactName(String keyword) {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       app.name      AS app_name,\n" +
                "       re_dev,\n" +
                "       support\n" +
                "FROM artifact,\n" +
                "     app,\n" +
                "     artifact_app\n" +
                "WHERE artifact.id = artifact_app.artifact_id\n" +
                "  AND app.id = artifact_app.app_id" +
                "  AND artifact.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactAppDto.class));
    }


}
