package cn.whu.wy.osgov.dto.response;

/**
 * 错误码定义，可参考阿里巴巴开发手册
 *
 * @author WangYong
 * Date 2022/05/05
 * Time 16:39
 */
public enum ResponseCode {

    SUCCESS(0, "成功"),
    FAILURE(1, "服务异常，请稍后再试"),


    NOT_FOUND(100, "目标不存在");


    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
