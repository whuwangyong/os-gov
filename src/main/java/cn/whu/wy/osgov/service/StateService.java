package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.dto.state.ArtifactRisk;
import cn.whu.wy.osgov.dto.state.StateDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 09:43
 */
@Service
public class StateService extends BaseService {
    public StateService(DataSource dataSource) {
        super(dataSource);
    }

    public StateDto get() {
        Integer appNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM app", Integer.class);
        Integer artifactNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM artifact", Integer.class);
        Integer hostNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM host", Integer.class);
        Integer licenseNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM license", Integer.class);
        Integer tagNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM tag", Integer.class);

        // 按风险系数递减排序的组件，top-100
        String sql = "SELECT a.id                   AS artifact_id,\n" +
                "       a.org                       AS artifact_org,\n" +
                "       a.name                      AS artifact_name,\n" +
                "       a.version                   AS artifact_version,\n" +
                "       SUM(v.level * v.difficulty) AS risk\n" +
                "FROM artifact AS a,\n" +
                "     vulnerability AS v,\n" +
                "     artifact_vulnerability AS av\n" +
                "WHERE a.id = av.artifact_id\n" +
                "  AND v.id = av.vulnerability_id\n" +
                "GROUP BY artifact_id\n" +
                "ORDER BY risk DESC LIMIT 100";
        List<ArtifactRisk> artifactRisks = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactRisk.class));
        return StateDto.builder()
                .appNum(appNum)
                .artifactNum(artifactNum)
                .hostNum(hostNum)
                .licenseNum(licenseNum)
                .tagNum(tagNum)
                .artifactRisks(artifactRisks)
                .build();
    }
}
