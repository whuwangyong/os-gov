package cn.whu.wy.osgov.service;

import cn.whu.wy.osgov.dto.statistics.ArtifactRiskDto;
import cn.whu.wy.osgov.dto.statistics.StatisticsDto;
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
public class StatisticsService extends BaseService {
    public StatisticsService(DataSource dataSource) {
        super(dataSource);
    }

    public StatisticsDto get() {
        Integer appNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM app", Integer.class);
        Integer hostNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM host", Integer.class);
        Integer licenseNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM license", Integer.class);
        Integer tagNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM tag", Integer.class);
        Integer artifactNum = jdbcTemplate.queryForObject("SELECT COUNT(id) FROM artifact", Integer.class);

        Integer artifactVul4Num = jdbcTemplate.queryForObject(
                "SELECT COUNT(DISTINCT artifact_id)\n" +
                        "FROM artifact_vulnerability AS av,\n" +
                        "     vulnerability AS v\n" +
                        "WHERE v.id = av.vulnerability_id\n" +
                        "  AND level = 4", Integer.class);
        Integer artifactVul3Num = jdbcTemplate.queryForObject(
                "SELECT COUNT(DISTINCT artifact_id)\n" +
                        "FROM artifact_vulnerability AS av,\n" +
                        "     vulnerability AS v\n" +
                        "WHERE v.id = av.vulnerability_id\n" +
                        "  AND level = 3", Integer.class);
        Integer artifactVul2Num = jdbcTemplate.queryForObject(
                "SELECT COUNT(DISTINCT artifact_id)\n" +
                        "FROM artifact_vulnerability AS av,\n" +
                        "     vulnerability AS v\n" +
                        "WHERE v.id = av.vulnerability_id\n" +
                        "  AND level = 2", Integer.class);
        Integer artifactVul1Num = jdbcTemplate.queryForObject(
                "SELECT COUNT(DISTINCT artifact_id)\n" +
                        "FROM artifact_vulnerability AS av,\n" +
                        "     vulnerability AS v\n" +
                        "WHERE v.id = av.vulnerability_id\n" +
                        "  AND level = 1", Integer.class);

        Integer artifactLicenseRisk0Num = jdbcTemplate.queryForObject("SELECT COUNT(artifact_id)\n" +
                "FROM artifact_license AS a,\n" +
                "     license AS l\n" +
                "WHERE a.license_id = l.id\n" +
                "  AND risk = 0", Integer.class);
        Integer artifactLicenseRisk1Num = jdbcTemplate.queryForObject("SELECT COUNT(artifact_id)\n" +
                "FROM artifact_license AS a,\n" +
                "     license AS l\n" +
                "WHERE a.license_id = l.id\n" +
                "  AND risk = 1", Integer.class);
        Integer artifactLicenseRisk2Num = jdbcTemplate.queryForObject("SELECT COUNT(artifact_id)\n" +
                "FROM artifact_license AS a,\n" +
                "     license AS l\n" +
                "WHERE a.license_id = l.id\n" +
                "  AND risk = 2", Integer.class);
        Integer artifactLicenseRisk3Num = jdbcTemplate.queryForObject("SELECT COUNT(artifact_id)\n" +
                "FROM artifact_license AS a,\n" +
                "     license AS l\n" +
                "WHERE a.license_id = l.id\n" +
                "  AND risk = 3", Integer.class);


        StatisticsDto statisticsDto = StatisticsDto.builder()
                .appNum(appNum)
                .artifactNum(artifactNum)
                .artifactVul0Num(artifactNum - artifactVul1Num - artifactVul2Num - artifactVul3Num - artifactVul4Num)
                .artifactVul1Num(artifactVul1Num)
                .artifactVul2Num(artifactVul2Num)
                .artifactVul3Num(artifactVul3Num)
                .artifactVul4Num(artifactVul4Num)
                .hostNum(hostNum)
                .licenseNum(licenseNum)
                .tagNum(tagNum)
                .artifactLicenseRisk0Num(artifactLicenseRisk0Num)
                .artifactLicenseRisk1Num(artifactLicenseRisk1Num)
                .artifactLicenseRisk2Num(artifactLicenseRisk2Num)
                .artifactLicenseRisk3Num(artifactLicenseRisk3Num)
                .build();

        // 健康度计算公式：(漏洞健康度+协议健康度)/2
        // 漏洞健康度=无漏洞的制品数/总数
        double gradeVul = 1.0 * statisticsDto.getArtifactVul0Num() / statisticsDto.getArtifactNum();
        // 协议健康度=无传染的制品数/总数
        double gradeLicense = 1.0 * statisticsDto.getArtifactLicenseRisk0Num() / statisticsDto.getArtifactNum();
        double grade = (gradeVul + gradeLicense) / 2;
        statisticsDto.setGrade(grade);
        return statisticsDto;
    }

    /**
     * 按风险系数递减排序的组件，top-100
     */
    public List<ArtifactRiskDto> top() {
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
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ArtifactRiskDto.class));
    }
}
