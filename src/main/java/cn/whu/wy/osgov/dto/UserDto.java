package cn.whu.wy.osgov.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 用户表
 *
 * @author WangYong
 * Date 2022/05/05
 * Time 14:13
 */
@Data
@Builder
public class UserDto {
    private String name;
    private String email;
    private String address;
}
