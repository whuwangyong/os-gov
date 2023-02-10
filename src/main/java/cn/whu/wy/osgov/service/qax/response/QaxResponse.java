package cn.whu.wy.osgov.service.qax.response;

import lombok.Data;

/**
 * @Author WangYong
 * @Date 2022/09/27
 * @Time 19:00
 */
@Data
public class QaxResponse<T> {

    private int code;
    private String message;
    private T data;
}
