package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:27
 */

@Repository
public class TagRepository extends BaseRepository<Tag> {

    public TagRepository(DataSource dataSource) {
        super(dataSource, Tag.class);
    }

    @Override
    String tableName() {
        return "tag";
    }
}
