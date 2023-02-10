package cn.whu.wy.osgov.entity;

import cn.whu.wy.osgov.service.qax.bean.TaskComponentWithMd5;
import cn.whu.wy.osgov.utils.VersionSortable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 10:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artifact extends Entity implements VersionSortable {

    // 唯一索引
    private String qaxId;

    // 前端和go module不适用
    private String org;

    private String name;
    private String version;

    private LocalDate publishDate;

    // 制品来源
    private String author;

    // 制品在系统中扮演的角色
    private String role;

    private String env;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return Objects.equals(org, artifact.org) &&
                name.equals(artifact.name) &&
                version.equals(artifact.version);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString("aaa".split(":")));
    }

    @Override
    public int hashCode() {
        return Objects.hash(org, name, version);
    }

    public static Artifact convertFrom(TaskComponentWithMd5.Component qaxComponent) {
        Artifact artifact = new Artifact();
        artifact.setQaxId(qaxComponent.getComponentId());
        List<String> coordinates = qaxComponent.getCoordinates();
        if (coordinates == null || coordinates.size() != 1) {
            System.out.println("qaxComponent coordinates wrong!, qaxComponent=+" + qaxComponent);
            return null;
        } else {
            String[] split = coordinates.get(0).split(":");
            if (split.length == 1 && StringUtils.hasText(qaxComponent.getComponentVersion())) {
                artifact.setName(split[0]);
                artifact.setVersion(qaxComponent.getComponentVersion());
            } else if (split.length == 2) {
                artifact.setName(split[0]);
                artifact.setVersion(split[1]);
            } else if (split.length == 3) {
                artifact.setOrg(split[0]);
                artifact.setName(split[1]);
                artifact.setVersion(split[2]);
            } else {
                return null;
            }
            if (qaxComponent.getUpdateDate() != null) {
                artifact.setPublishDate(LocalDate.parse(qaxComponent.getUpdateDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (qaxComponent.getComponentType() == 1) {
                artifact.setAuthor(Author.OpenSource.getValue());
            } else {
                artifact.setAuthor(Author.Independent.getValue());
            }
            artifact.setRole(Role.Component.getValue());
            artifact.setEnv(Env.PRODUCTION.getValue());
            artifact.setDescription(qaxComponent.getComponentDescription());
            return artifact;
        }
    }

    /**
     * 组件来源，作者
     */
    public enum Author {

        OpenSource("开源"),

        Independent("自研"),

        Commercial("商业"),

        XinChuang("信创");


        private final String value;

        Author(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 按粒度对开源制品进行分类
     */
    public enum Role {

        Software("软件"),

        Component("组件"),


        Tool("工具"),

        NA("NA");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Env {
        PRODUCTION("生产库"),
        DEV_TEST("开发测试库"),
        NEW("未入库");

        private final String value;

        Env(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
