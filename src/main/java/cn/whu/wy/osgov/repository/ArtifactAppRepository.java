package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.ArtifactApp;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/24
 * Time 19:50
 */
@Repository
public class ArtifactAppRepository extends BaseRepository<ArtifactApp> {

    public ArtifactAppRepository(DataSource dataSource) {
        super(dataSource, ArtifactApp.class);
    }

    @Override
    String tableName() {
        return "artifact_app";
    }
}
