package cn.whu.wy.osgov.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * {
 *   "status": 0,
 *   "msg": "",
 *   "data": {
 *     "items": [
 *       {
 *         // 每一行的数据
 *         "id": 1,
 *         "xxx": "xxxx"
 *       }
 *     ],
 *
 *     "total": 200 // 注意！！！这里不是当前请求返回的 items 的长度，而是数据库中一共有多少条数据，用于生成分页组件
 *     // 如果你不想要分页，把这个不返回就可以了。
 *
 *     // 如果无法知道数据总数，只能知道是否有下一页，请返回如下格式，amis 会简单生成一个简单版本的分页控件。
 *     "hasNext": true // 是否有下一页。
 *   }
 * }
 * status: 返回 0，表示当前接口正确返回，否则按错误请求处理；
 * msg: 返回接口处理信息，主要用于表单提交或请求失败时的 toast 显示；
 * data: 必须返回一个具有 key-value 结构的对象。
 * - items或rows：用于返回数据源数据，格式是数组
 * - total: 用于返回数据库中一共有多少条数据，用于生成分页
 *
 *
 *
 * @author WangYong
 * Date 2022/05/05
 * Time 16:33
 */
@Data
public class ResponseEntity implements Serializable {

    private int status;
    private String msg;
    private Map<String, Object> data;


    public ResponseEntity() {
    }

    public ResponseEntity(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseEntity(ResponseCode responseCode) {
        this.status = responseCode.code();
        this.msg = responseCode.msg();
    }

    public ResponseEntity(ResponseCode responseCode, Map<String, Object> data) {
        this.status = responseCode.code();
        this.msg = responseCode.msg();
        this.data = data;
    }

    public <T> ResponseEntity(ResponseCode responseCode, List<T> items) {
        this.status = responseCode.code();
        this.msg = responseCode.msg();
        this.data.put("items", items);
    }

    public <T> ResponseEntity(ResponseCode responseCode, List<T> items, long total) {
        this.status = responseCode.code();
        this.msg = responseCode.msg();
        this.data.put("items", items);
        this.data.put("total", total);
    }

    public <T> ResponseEntity(ResponseCode responseCode, List<T> items, boolean hasNext) {
        this.status = responseCode.code();
        this.msg = responseCode.msg();
        this.data.put("items", items);
        this.data.put("hasNext", hasNext);
    }

}
