package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.dto.ArtifactTagDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 09:14
 */
@Service
public class ArtifactTagService extends BaseService {

    public ArtifactTagService(DataSource dataSource) {
        super(dataSource);
    }

    public List<ArtifactTagDto> get() {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       tag.name  AS tag_name\n" +
                "FROM artifact,\n" +
                "     tag,\n" +
                "     artifact_tag\n" +
                "WHERE artifact.id = artifact_tag.artifact_id\n" +
                "  AND tag.id = artifact_tag.tag_id";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactTagDto.class));
    }

    public List<ArtifactTagDto> queryByArtifactName(String keyword) {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       tag.name  AS tag_name\n" +
                "FROM artifact,\n" +
                "     tag,\n" +
                "     artifact_tag\n" +
                "WHERE artifact.id = artifact_tag.artifact_id\n" +
                "  AND tag.id = artifact_tag.tag_id" +
                "  AND artifact.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactTagDto.class));
    }

    public List<ArtifactTagDto> queryByTagName(String keyword) {
        String sql = "SELECT artifact.id   AS artifact_id,\n" +
                "       artifact.name AS artifact_name,\n" +
                "       tag.name  AS tag_name\n" +
                "FROM artifact,\n" +
                "     tag,\n" +
                "     artifact_tag\n" +
                "WHERE artifact.id = artifact_tag.artifact_id\n" +
                "  AND tag.id = artifact_tag.tag_id" +
                "  AND tag.name LIKE '%" + keyword + "%'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactTagDto.class));
    }
}
