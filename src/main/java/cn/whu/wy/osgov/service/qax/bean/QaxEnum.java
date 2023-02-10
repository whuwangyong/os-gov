package cn.whu.wy.osgov.service.qax.bean;

import java.util.Map;

/**
 * @Author WangYong
 * @Date 2022/11/30
 * @Time 15:02
 */
public interface QaxEnum {
    // 漏洞等级
    Map<Integer, String> vulnerabilityLevel = Map.of(
            1, "超危漏洞",
            2, "高危漏洞",
            4, "中危漏洞",
            8, "低危漏洞",
            16, "未知漏洞"
    );

    // 漏洞利用难度
    Map<Integer, String> vulnerabilityUtilizeDegree = Map.of(
            1, "容易",
            2, "一般",
            4, "困难",
            16, "未知"
    );

    // 许可协议等级
    Map<Integer, String> LicenseLevel = Map.of(
            1, "超危",
            2, "高危",
            4, "中危",
            8, "低危",
            16, "未知"
    );

    // 组件等级
    Map<Integer, String> componentLevel = Map.of(
            1, "超危组件",
            2, "高危组件",
            4, "中危组件",
            8, "低危组件",
            16, "未知风险组件",
            32, "无漏洞组件",
            64, "未知版本组件"
    );


    // 项目类型
    Map<Integer, String> projectType = Map.of(
            10, "成分分析项目",
            12, "二进制项目"
    );


}
