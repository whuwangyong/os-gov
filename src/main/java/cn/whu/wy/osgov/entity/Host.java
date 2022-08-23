package cn.whu.wy.osgov.entity;

import lombok.*;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 13:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Host extends Entity {

    private String ip;
    private String hardware;
    private String network;
}
