package cn.whu.wy.osgov.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 09:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto {

    // 健康度[0-1]，计算方法见StateService
    private double grade;

    private int appNum;
    private int hostNum;
    private int licenseNum;
    private int tagNum;

    private int artifactNum;
    private int artifactVul4Num; // 有严重漏洞的制品数量
    private int artifactVul3Num; // 有高危漏洞的制品数量
    private int artifactVul2Num; // 有中危漏洞的制品数量
    private int artifactVul1Num; // 有低危漏洞的制品数量
    private int artifactVul0Num; // 无漏洞的制品数量

    private int artifactLicenseRisk3Num; // 高传染性开源协议的制品数量
    private int artifactLicenseRisk2Num; // 中传染性开源协议的制品数量
    private int artifactLicenseRisk1Num; // 低传染性开源协议的制品数量
    private int artifactLicenseRisk0Num; // 无传染性开源协议的制品数量
}
