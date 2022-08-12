package cn.whu.wy.osgov.dto.response;

import java.util.List;

/**
 * @author WangYong
 * Date 2022/08/09
 * Time 10:05
 */
public class Data<T> {

    List<T> items;

    boolean hasNext;
}
