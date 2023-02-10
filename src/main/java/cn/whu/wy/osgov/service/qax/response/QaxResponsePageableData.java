package cn.whu.wy.osgov.service.qax.response;

import lombok.Data;

import java.util.List;

/**
 * 该类是QaxResponse里data字段的一种类型，是一种具备分页参数的data
 *
 * @Author WangYong
 * @Date 2022/12/01
 * @Time 12:22
 */
@Data
public class QaxResponsePageableData<T> {

    int pageIndex;
    int pageSize;
    int total;
    List<T> content;
}
