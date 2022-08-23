package cn.whu.wy.osgov.entity;

import lombok.*;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 13:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class App extends Entity {

    private String name;
    private String env;
}
