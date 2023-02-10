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
    private int artifactsWithSeriousVul; // 有严重漏洞的制品数量
    private int artifactsWithHighVul; // 有高危漏洞的制品数量
    private int artifactsWithMiddleVul; // 有中危漏洞的制品数量
    private int artifactsWithLowVul; // 有低危漏洞的制品数量
    private int artifactsWithUnknownVul; // 有未知漏洞的制品数量
    private int artifactsWithNoneVul; // 无漏洞的制品数量

    private int artifactsWithSeriousRiskLicense; // 超高传染性开源协议的制品数量
    private int artifactsWithHighRiskLicense; // 高传染性开源协议的制品数量
    private int artifactsWithMiddleRiskLicense; // 中传染性开源协议的制品数量
    private int artifactsWithLowRiskLicense; // 低传染性开源协议的制品数量
    private int artifactsWithUnknownRiskLicense; // 未知传染性开源协议的制品数量
}
