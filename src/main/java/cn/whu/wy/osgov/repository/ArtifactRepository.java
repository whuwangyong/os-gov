package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.artifact.Artifact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 15:26
 */
@Repository
@Slf4j
public class ArtifactRepository extends BaseRepository<Artifact> {

    public ArtifactRepository(DataSource dataSource) {
        super(dataSource, Artifact.class);
    }

    @Override
    String tableName() {
        return "artifact";
    }
}
