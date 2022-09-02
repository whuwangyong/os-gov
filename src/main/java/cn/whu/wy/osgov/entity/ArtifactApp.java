package cn.whu.wy.osgov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author WangYong
 * Date 2022/09/02
 * Time 11:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactApp extends Entity {

    private int artifactId;
    private int appId;
    private boolean reDev;
    private boolean support;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactApp that = (ArtifactApp) o;
        return artifactId == that.artifactId && appId == that.appId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactId, appId);
    }
}
