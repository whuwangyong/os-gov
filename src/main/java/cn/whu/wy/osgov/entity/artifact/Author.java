package cn.whu.wy.osgov.entity.artifact;

/**
 * 组件来源，作者
 *
 * @author WangYong
 * Date 2022/08/16
 * Time 20:22
 */
public enum Author {

    OpenSource("开源"),

    Independent("自研"),

    Commercial("商业"),

    XinChuang("信创");


    final String value;

    Author(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
