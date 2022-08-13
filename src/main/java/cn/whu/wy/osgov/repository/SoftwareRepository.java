package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Software;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/13
 * Time 15:15
 */
@Repository
public class SoftwareRepository extends BaseRepository<Software> {


    @Autowired
    public SoftwareRepository(DataSource dataSource) {
        super(dataSource, Software.class);
    }

    @Override
    String tableName() {
        return "software";
    }
}
