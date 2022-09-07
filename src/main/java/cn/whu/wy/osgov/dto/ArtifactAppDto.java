package cn.whu.wy.osgov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangYong
 * Date 2022/09/05
 * Time 21:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtifactAppDto {
    private int artifactId;
    private String artifactName;
    private String appName;
    private boolean reDev;
    private boolean support;
}
