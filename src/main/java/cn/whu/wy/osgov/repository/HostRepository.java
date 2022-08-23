package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Host;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:25
 */
@Repository
public class HostRepository extends BaseRepository<Host> {

    public HostRepository(DataSource dataSource) {
        super(dataSource, Host.class);
    }

    @Override
    String tableName() {
        return "host";
    }
}
