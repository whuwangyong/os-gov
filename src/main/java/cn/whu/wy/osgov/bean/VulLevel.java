package cn.whu.wy.osgov.bean;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 10:57
 */
public enum VulLevel {
    SERIOUS(0, "严重"),
    HIGH(1, "高危"),
    MIDDLE(2, "中危"),
    LOW(3, "低危");

    int code;
    String desc;

    VulLevel(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
