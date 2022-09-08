package cn.whu.wy.osgov.dto.statistics;

import lombok.Data;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 20:36
 */
@Data
public class ArtifactRiskDto {
    private int artifactId;
    private String artifactOrg;
    private String artifactName;
    private String artifactVersion;

    // 风险系数 = 组件的所有漏洞风险之和，每个漏洞的风险=风险等级*利用难度
    private int risk;
}
