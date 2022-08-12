package cn.whu.wy.osgov.entity;

import cn.whu.wy.osgov.utils.VersionSortable;
import lombok.*;

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
public class Component extends Entity implements VersionSortable {

    // 索引
    private String groupId;
    // 索引
    private String artifactId;
    private String version;
}
