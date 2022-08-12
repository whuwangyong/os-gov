package cn.whu.wy.osgov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author WangYong
 * Date 2022/05/05
 * Time 14:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 自增id，主键，用于分页
    private int id;
    // 唯一索引
    private String email;
    // 索引
    private String name;
    private String address;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
