package cn.whu.wy.osgov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author WangYong
 * Date 2022/08/24
 * Time 19:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactTag extends Entity {

    private int artifactId;
    private int tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactTag that = (ArtifactTag) o;
        return artifactId == that.artifactId && tagId == that.tagId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactId, tagId);
    }
}
