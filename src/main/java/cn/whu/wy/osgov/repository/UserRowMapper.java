package cn.whu.wy.osgov.repository;

import cn.whu.wy.osgov.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author WangYong
 * Date 2022/05/07
 * Time 16:51
 */
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setCreatedTime(rs.getTimestamp("created_time").toLocalDateTime());
        user.setUpdatedTime(rs.getTimestamp("updated_time").toLocalDateTime());
        return user;
    }
}
