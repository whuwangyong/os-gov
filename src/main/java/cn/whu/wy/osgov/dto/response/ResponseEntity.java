package cn.whu.wy.osgov.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {
 * "status": 0,
 * "msg": "",
 * "data": {
 * "items": [
 * {
 * // 每一行的数据
 * "id": 1,
 * "xxx": "xxxx"
 * }
 * ],
 * <p>
 * "total": 200 // 注意！！！这里不是当前请求返回的 items 的长度，而是数据库中一共有多少条数据，用于生成分页组件
 * // 如果你不想要分页，把这个不返回就可以了。
 * <p>
 * // 如果无法知道数据总数，只能知道是否有下一页，请返回如下格式，amis 会简单生成一个简单版本的分页控件。
 * "hasNext": true // 是否有下一页。
 * }
 * }
 * status: 返回 0，表示当前接口正确返回，否则按错误请求处理；
 * msg: 返回接口处理信息，主要用于表单提交或请求失败时的 toast 显示；
 * data: 必须返回一个具有 key-value 结构的对象。
 * - items或rows：用于返回数据源数据，格式是数组
 * - total: 用于返回数据库中一共有多少条数据，用于生成分页
 *
 * @author WangYong
 * Date 2022/05/05
 * Time 16:33
 */
@Data
public class ResponseEntity implements Serializable {

    private int status;
    private String msg;
    private Map<String, Object> data = new HashMap<>();

    private static final String DATA_KEY_ITEMS = "items";
    private static final String DATA_KEY_TOTAL = "total";
    private static final String DATA_KEY_HAS_NEXT = "hasNext";


    public ResponseEntity() {
    }

    private ResponseEntity(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseEntity(ResponseCode responseCode) {
        this.status = responseCode.code();
        this.msg = responseCode.msg();
    }


    /**
     * 无具体数据返回的通用成功响应
     *
     * @return
     */
    public static ResponseEntity success() {
        return new ResponseEntity(ResponseCode.SUCCESS);
    }


    /**
     * 仅返回一条记录，包装到items里面。不用翻页
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity success(T t) {
        ResponseEntity response = new ResponseEntity(ResponseCode.SUCCESS);
        response.setData(Map.of(DATA_KEY_ITEMS, List.of(t)));
        return response;
    }

    /**
     * 返回多条记录，包装到items里面。不分页。用于返回查询结果
     *
     * @param items
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity success(List<T> items) {
        ResponseEntity response = new ResponseEntity(ResponseCode.SUCCESS);
        response.setData(Map.of(DATA_KEY_ITEMS, items));
        return response;
    }

    /**
     * 返回多条记录，包装到items里面。同时返回了db里面的总数（非items.size），便于前端实现翻页
     *
     * @param items
     * @param total
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity success(List<T> items, long total) {
        ResponseEntity response = new ResponseEntity(ResponseCode.SUCCESS);
        response.setData(Map.of(DATA_KEY_ITEMS, items, DATA_KEY_TOTAL, total));
        return response;
    }

    /**
     * 返回多条记录，包装到items里面。在不知道总数的情况下，返回是否还有下一条记录，便于前端实现翻页
     *
     * @param items
     * @param hasNext
     * @param <T>
     * @return
     */
    public <T> ResponseEntity success(List<T> items, boolean hasNext) {
        ResponseEntity response = new ResponseEntity(ResponseCode.SUCCESS);
        response.setData(Map.of(DATA_KEY_ITEMS, items, DATA_KEY_HAS_NEXT, hasNext));
        return response;
    }

    /**
     * 通用的失败响应
     *
     * @return
     */
    public static ResponseEntity fail() {
        return new ResponseEntity(ResponseCode.FAILURE);
    }

    /**
     * 使用通用失败响应码，同时指定失败消息
     *
     * @param msg
     * @return
     */
    public static ResponseEntity fail(String msg) {
        return new ResponseEntity(ResponseCode.FAILURE.code(), msg);
    }

    /**
     * 具体的失败响应
     *
     * @param responseCode
     * @return
     */
    public static ResponseEntity fail(ResponseCode responseCode) {
        return new ResponseEntity(responseCode);
    }

    /**
     * 具体的失败响应
     *
     * @param status
     * @param msg
     * @return
     */
    public static ResponseEntity fail(int status, String msg) {
        return new ResponseEntity(status, msg);
    }


}
