package cn.whu.wy.osgov.entity.component;

import cn.whu.wy.osgov.entity.Entity;
import cn.whu.wy.osgov.utils.VersionSortable;
import lombok.*;

import java.util.List;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 10:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artifact extends Entity implements VersionSortable {

    // 前端和go module不适用
    private String groupId;

    private String artifactId;
    private String version;

    // 给组件打标签，用于分类。一个组件可有多个标签。
    private List<Tag> tags;

    // 来源
    private Author author;
    private License license;

    // 是否有漏洞
    private boolean exposed;

    // 是否二次开发
    private boolean reDev;

    // 是否有厂商支持
    private boolean support;

}
