package cn.whu.wy.osgov.entity;

import lombok.*;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 11:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends Entity {

    private String name;
}
