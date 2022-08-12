package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 15:26
 */
@Repository
@Slf4j
public class ComponentRepository extends BaseRepository<Component> {

    @Autowired
    public ComponentRepository(DataSource dataSource) {
        super(dataSource, Component.class);
    }

    @Override
    String tableName() {
        return "component";
    }
}
