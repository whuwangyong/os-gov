package cn.whu.wy.osgov.dto.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 09:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {
    private Integer appNum;
    private Integer artifactNum;
    private Integer hostNum;
    private Integer licenseNum;
    private Integer tagNum;

    private List<ArtifactRisk> artifactRisks;

}
