package cn.whu.wy.osgov.entity;

import lombok.*;

/**
 * @author WangYong
 * Date 2022/08/16
 * Time 20:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class License extends Entity {

    private String name;
    private byte risk;

}
