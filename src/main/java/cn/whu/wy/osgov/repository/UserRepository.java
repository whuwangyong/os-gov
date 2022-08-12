package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.User;
import cn.whu.wy.osgov.exception.UserNotFundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据层，操作user表的一些方法
 *
 * @author WangYong
 * Date 2022/05/07
 * Time 11:01
 */
@Repository
@Slf4j
public class UserRepository {

    private final NamedParameterJdbcTemplate npJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public UserRepository(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("user").usingGeneratedKeyColumns("id");
        npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

    }

    public int add(User user) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        Number number = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
//        log.info("id={}", number);
        return number.intValue();
    }

    public User findById(int id) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            User user = npJdbcTemplate.queryForObject("select * from user where id = :id", params, new UserRowMapper());
            log.info("user={}", user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFundException("id=" + id);
        }
    }

    public User findByEmail(String email) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            User user = npJdbcTemplate.queryForObject("select * from user where email = :email", params, new UserRowMapper());
            log.info("user={}", user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFundException("email=" + email);
        }
    }

    public List<User> findByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        List<User> users = npJdbcTemplate.query("select * from user where name = :name", params, new UserRowMapper());
        log.info("users={}", users);
        return users;
    }

    public Integer count() {
        Integer count = npJdbcTemplate.getJdbcTemplate().queryForObject("select count(*) from user", Integer.class);
        log.info("count={}", count);
        count = npJdbcTemplate.queryForObject("select count(*) from user", new EmptySqlParameterSource(), Integer.class);
        log.info("count={}", count);
        return count;
    }

    // 分页查询
    // 支持id不连续的情况
    // 假设pageSize=100，page=3，则查询的范围是201-300
    public List<User> get(int pageSize, int page) {
        Integer total = count();
        if (total == 0) return null;

        // 跳过的记录数
        int skips = pageSize * (page - 1);

        // 超过查询范围，直接返回最后一条
        if (skips >= total) {
            skips = total - 1;
        }

        Integer firstId = npJdbcTemplate.getJdbcTemplate().queryForObject("select id from user order by id limit ?,1", Integer.class, skips);
        Map<String, Object> params = new HashMap<>();
        params.put("firstId", firstId);
        params.put("pageSize", pageSize);
        List<User> users = npJdbcTemplate.query("select * from user where id >= :firstId order by id limit :pageSize", params, new UserRowMapper());
        log.info("users={}", users);
        return users;
    }

    public int delete(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        int res = npJdbcTemplate.update("delete from user where id = :id", params);
        log.info("res={}", res);
        return res;
    }

    public User update(User user) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        int res = npJdbcTemplate.update(
                "update user set name=:name, email=:email, address=:address, updated_time=:updatedTime where id=:id",
                sqlParameterSource);
        log.info("res={}", res);
        return findById(user.getId());
    }
}
