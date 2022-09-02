package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.AppHost;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:18
 */
@Repository
public class AppHostRepository extends BaseRepository<AppHost> {

    public AppHostRepository(DataSource dataSource) {
        super(dataSource, AppHost.class);
    }

    @Override
    String tableName() {
        return "app_host";
    }
}
