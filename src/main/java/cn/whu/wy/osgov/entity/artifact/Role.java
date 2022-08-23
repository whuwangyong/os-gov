package cn.whu.wy.osgov.entity.artifact;

/**
 * 按粒度对开源制品进行分类
 *
 * @author WangYong
 * Date 2022/08/20
 * Time 16:30
 */
public enum Role {

    Software("软件"),

    Component("组件"),


    Tool("工具"),

    NA("NA");

    final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
