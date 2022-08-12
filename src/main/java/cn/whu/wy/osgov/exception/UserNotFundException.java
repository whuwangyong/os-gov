package cn.whu.wy.osgov.exception;

/**
 * @author WangYong
 * Date 2022/05/06
 * Time 11:37
 */
public class UserNotFundException extends RuntimeException {
    public UserNotFundException(String s) {
        super("Could not find user: " + s);
    }
}
