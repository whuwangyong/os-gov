package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.App;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:18
 */
@Repository
public class AppRepository extends BaseRepository<App> {

    public AppRepository(DataSource dataSource) {
        super(dataSource, App.class);
    }

    @Override
    String tableName() {
        return "app";
    }
}
