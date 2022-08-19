package cn.whu.wy.osgov.entity.component;

/**
 * @author WangYong
 * Date 2022/08/16
 * Time 20:40
 */

public enum Tag {

    DEV_TOOL("开发工具"),

    DEV_FRAMEWORK("开发框架"),

    MQ("消息队列"),

    DB("数据库"),

    MONITOR("监控"),

    CONTAINER("容器"),

    VISUALIZATION("可视化"),

    LB("负载均衡"),

    LOG("日志");

    String name;

    Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
