package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Ask;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @Author WangYong
 * @Date 2022/09/23
 * @Time 15:40
 */
@Repository
public class AskRepository extends BaseRepository<Ask> {


    public AskRepository(DataSource dataSource) {
        super(dataSource, Ask.class);
    }

    @Override
    String tableName() {
        return "ask";
    }
}
