package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Entity;
import cn.whu.wy.osgov.exception.NotFundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 15:29
 */
@Slf4j
public abstract class BaseRepository<T extends Entity> {

    protected final JdbcTemplate jdbcTemplate;
    protected final NamedParameterJdbcTemplate npJdbcTemplate;
    protected final SimpleJdbcInsert simpleJdbcInsert;
    protected final RowMapper<T> rowMapper;


    abstract String tableName();

    public BaseRepository(DataSource dataSource, Class<T> typeParameterClass) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(tableName()).usingGeneratedKeyColumns("id");
        npJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.rowMapper = new BeanPropertyRowMapper<>(typeParameterClass);
    }

    /**
     * 新增一条记录
     */
    public int add(T t) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(t);
        Number number = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        log.info("add: {}, return key={}", t, number);
        return number.intValue();
    }

    /**
     * 根据主键id查找一条记录
     */
    public T findById(int id) {
        try {
            T t = jdbcTemplate.queryForObject("select * from " + tableName() + " where id = ? ", rowMapper, id);
            log.info("findById: id={}, res={}", id, t);
            return t;
        } catch (EmptyResultDataAccessException e) {
            String s = String.format("table=%s, id=%d", tableName(), id);
            throw new NotFundException(s, e);
        }
    }

    /**
     * 查询表里总的记录数
     */
    public Integer count() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from " + tableName(), Integer.class);
        log.info("count: {}", count);
        return count;
    }

    /**
     * 分页查询
     * 支持id不连续的情况
     * 假设pageSize=100，page=3，则查询的范围是201-300
     */
    public List<T> get(int pageSize, int page) {
        Integer total = count();
        if (total == 0) return Collections.emptyList();

        // 跳过的记录数
        int skips = pageSize * (page - 1);

        // 若超过查询范围，直接返回最后一条
        if (skips >= total) {
            skips = total - 1;
        }

        Integer startId = jdbcTemplate.queryForObject("select id from " + tableName() + " order by id limit ?,1", Integer.class, skips);
        List<T> list = jdbcTemplate.query("select * from " + tableName() + " where id >= ? order by id limit ?", rowMapper, startId, pageSize);
        log.info("get: pageSize={}, page={}, result.size={}", pageSize, page, list.size());
        return list;
    }

    /**
     * 根据条件模糊查询。不支持分页
     *
     * @param params
     * @return
     */
    public List<T> query(Map<String, String> params) {
        StringBuilder sb = new StringBuilder("select * from " + tableName() + " where ");
        params.forEach((k, v) -> sb.append(k).append(" like '%").append(v).append("%' or "));
        // 删除末尾多余的or
        sb.delete(sb.length() - 4, sb.length());

        return jdbcTemplate.query(sb.toString(), rowMapper);
    }


    /**
     * 根据主键删除一条记录
     */
    public int delete(int id) {
        int res = jdbcTemplate.update("delete from " + tableName() + " where id = ?", id);
        log.info("delete, res={}", res);
        return res;
    }

    /**
     * 采用先删除，再插入的方式进行更新。数据的主键id会变。
     *
     * @param t 全对象覆盖更新，不允许里面有空字段
     */
    @Transactional
    public boolean update(T t) {
        T old = findById(t.getId());
        if (old != null) {
            delete(old.getId());
            add(t);
            return true;
        } else {
            return false;
        }
    }


}
