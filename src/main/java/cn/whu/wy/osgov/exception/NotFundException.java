package cn.whu.wy.osgov.exception;

/**
 * @author WangYong
 * Date 2022/05/06
 * Time 11:37
 */
public class NotFundException extends RuntimeException {
    public NotFundException(String s, Throwable cause) {
        super("Could not find: " + s, cause);
    }
}
