package cn.whu.wy.osgov.entity;

import java.time.LocalDateTime;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 17:30
 */
public abstract class Entity {

    // 自增id，主键，用于排序分页
    protected int id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
