package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.Entity;
import cn.whu.wy.osgov.exception.NotFundException;
import cn.whu.wy.osgov.utils.ClassUtils;
import cn.whu.wy.osgov.utils.StringUtils;
import com.google.common.base.CaseFormat;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//        log.info("add: {}, return key={}", t, number);
        return number.intValue();
    }

    /**
     * 批量新增记录
     */
    public int[] add(List<T> tList) {
        if (tList.size() == 0) {
            return new int[]{0};
        }
        // 将列表转为sqlParameterSources
        List<BeanPropertySqlParameterSource> list = new ArrayList<>();
        tList.forEach(t -> list.add(new BeanPropertySqlParameterSource(t)));
        BeanPropertySqlParameterSource[] sqlParameterSources = list.toArray(new BeanPropertySqlParameterSource[0]);

        // 拼装sql，目标是：INSERT INTO t(col1,col2,col3) VALUES (:field1,:field2,:field3)
        StringBuilder sb = new StringBuilder("INSERT INTO " + tableName() + "(");
        Map<String, Object> fieldsValue = ClassUtils.getFieldsValue(tList.get(0));
        // id是自增的，不拼到sql里面
        List<String> fields = fieldsValue.keySet().stream().filter(e -> !e.equals("id")).collect(Collectors.toList());
        // 拼列名
        fields.forEach(filed -> {
            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, filed);
            sb.append(column).append(",");
        });
        StringUtils.deleteLastChars(sb, ",");
        sb.append(") VALUES (");
        // 拼namedParameters
        fields.forEach(filed -> sb.append(":").append(filed).append(","));
        StringUtils.deleteLastChars(sb, ",");
        sb.append(")");

        String sql = sb.toString();
        log.info("batch add, sql={}", sql);
        return npJdbcTemplate.batchUpdate(sql, sqlParameterSources);
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
        params.forEach((k, v) -> sb.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, k)).append(" like '%").append(v).append("%' or "));
        // 删除末尾多余的" or "
        StringUtils.deleteLastChars(sb, " or ");

        String sql = sb.toString();
        log.info("query: params={}, sql={}", params, sql);
        return jdbcTemplate.query(sql, rowMapper);
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
     * 逐字段更新。主键不变。
     * 注意：没有检查传入对象是否包含空字段。
     * 1. 可以由数据库进行约束
     * 2. 与前端约定，必须传入完成的对象，未改动的字段也必须一起传过来
     */
    public int update(T t) {
        if (findById(t.getId()) != null) {
            StringBuilder sb = new StringBuilder("update " + tableName() + " set ");
            Map<String, Object> fieldsValue = ClassUtils.getFieldsValue(t);
            fieldsValue.forEach((k, v) -> sb.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, k))
                    .append("='").append(v).append("', "));
            // 删掉末尾多余的', '
            StringUtils.deleteLastChars(sb, ", ");
            sb.append(" where id=").append(t.getId());
            String sql = sb.toString();
            log.info("update: sql={}", sql);
            return jdbcTemplate.update(sql);
        } else {
            return 0;
        }
    }

    /**
     * 采用先删除，再插入的方式进行更新。缺点是数据的主键id会变
     *
     * @param t 全对象覆盖更新，不允许里面有空字段
     */
    @Transactional
    @Deprecated
    public boolean replace(T t) {
        T old = findById(t.getId());
        if (old != null) {
            delete(old.getId());
            // Transactional注解有效。比如在这里进行除零操作，会发现delete未提交。
            // int a=2/0;
            add(t);
            return true;
        } else {
            return false;
        }
    }


}
