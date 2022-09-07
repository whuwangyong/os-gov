package cn.whu.wy.osgov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangYong
 * Date 2022/09/05
 * Time 20:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppHostDto {
    private int appId;
    private String appName;
    private String appEnv;
    private int hostId;
    private String ip;
    private String hardware;
    private String network;
}
