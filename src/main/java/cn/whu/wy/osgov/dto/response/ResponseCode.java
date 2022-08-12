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

    USER_NOT_FOUND(100, "用户不存在");


    int code;
    String msg;

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
