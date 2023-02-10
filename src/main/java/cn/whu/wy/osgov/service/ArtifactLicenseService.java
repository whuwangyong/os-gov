package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.dto.ArtifactLicenseDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 08:47
 */
@Service
public class ArtifactLicenseService extends BaseService {

    public ArtifactLicenseService(DataSource dataSource) {
        super(dataSource);
    }

    public List<ArtifactLicenseDto> get() {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       license.name  AS license_name,\n" +
                "       risk          AS license_risk\n" +
                "FROM artifact,\n" +
                "     license,\n" +
                "     artifact_license\n" +
                "WHERE artifact.id = artifact_license.artifact_id\n" +
                "  AND license.id = artifact_license.license_id ORDER BY artifact.id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactLicenseDto.class));
    }

    public List<ArtifactLicenseDto> queryByArtifactName(String keyword) {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       license.name  AS license_name,\n" +
                "       risk          AS license_risk\n" +
                "FROM artifact,\n" +
                "     license,\n" +
                "     artifact_license\n" +
                "WHERE artifact.id = artifact_license.artifact_id\n" +
                "  AND license.id = artifact_license.license_id" +
                "  AND artifact.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactLicenseDto.class));
    }

    public List<ArtifactLicenseDto> queryByLicenseName(String keyword) {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       license.name  AS license_name,\n" +
                "       risk          AS license_risk\n" +
                "FROM artifact,\n" +
                "     license,\n" +
                "     artifact_license\n" +
                "WHERE artifact.id = artifact_license.artifact_id\n" +
                "  AND license.id = artifact_license.license_id" +
                "  AND license.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactLicenseDto.class));
    }
}
