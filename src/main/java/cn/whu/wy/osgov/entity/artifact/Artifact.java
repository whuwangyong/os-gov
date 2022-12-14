package cn.whu.wy.osgov.entity.artifact;

import cn.whu.wy.osgov.entity.Entity;
import cn.whu.wy.osgov.utils.VersionSortable;
import lombok.*;

import java.time.LocalDate;

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
    private String org;

    private String name;
    private String version;

    private LocalDate publishDate;

    // 制品来源
    private String author;

    // 制品在系统中扮演的角色
    private String role;

    private String description;

}
