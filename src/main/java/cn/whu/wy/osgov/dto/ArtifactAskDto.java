package cn.whu.wy.osgov.dto;

import cn.whu.wy.osgov.entity.Artifact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author WangYong
 * @Date 2022/09/23
 * @Time 15:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtifactAskDto {

    private int askId;
    private String askUsername;
    private String askProject;
    private LocalDateTime askCommitTime;
    private String askStatus;
    private List<Artifact> artifacts;

    private String buildGradle;
}
