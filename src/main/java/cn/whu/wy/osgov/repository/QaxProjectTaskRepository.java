package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.qax.ProjectTask;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @Author WangYong
 * @Date 2022/09/28
 * @Time 17:34
 */
@Repository
public class QaxProjectTaskRepository extends BaseRepository<ProjectTask> {


    public QaxProjectTaskRepository(DataSource dataSource) {
        super(dataSource, ProjectTask.class);
    }

    @Override
    String tableName() {
        return "qax_project_task";
    }
}
