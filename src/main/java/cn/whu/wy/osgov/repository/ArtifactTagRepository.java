package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.ArtifactTag;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/24
 * Time 19:50
 */
@Repository
public class ArtifactTagRepository extends BaseRepository<ArtifactTag> {

    public ArtifactTagRepository(DataSource dataSource) {
        super(dataSource, ArtifactTag.class);
    }

    @Override
    String tableName() {
        return "artifact_tag";
    }
}
