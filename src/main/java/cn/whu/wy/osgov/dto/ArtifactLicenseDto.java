package cn.whu.wy.osgov.dto;

import lombok.Data;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 08:45
 */
@Data
public class ArtifactLicenseDto {

    private int artifactId;
    private String artifactName;
    private String licenseName;
    private String licenseRisk;
}
