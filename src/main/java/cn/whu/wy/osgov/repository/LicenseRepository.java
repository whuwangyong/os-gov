package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.License;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:29
 */
@Repository
public class LicenseRepository extends BaseRepository<License> {

    public LicenseRepository(DataSource dataSource) {
        super(dataSource, License.class);
    }

    @Override
    String tableName() {
        return "license";
    }
}
