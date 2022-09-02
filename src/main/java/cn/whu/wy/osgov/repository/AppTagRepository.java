package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.ArtifactTag;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:18
 */
@Repository
public class AppTagRepository extends BaseRepository<ArtifactTag> {

    public AppTagRepository(DataSource dataSource) {
        super(dataSource, ArtifactTag.class);
    }

    @Override
    String tableName() {
        return "app_tag";
    }
}
