package cn.whu.wy.osgov.utils;

/**
 * @author WangYong
 * Date 2022/09/02
 * Time 15:31
 */
public class StringUtils {

    /**
     * 删除末尾的字符串
     */
    public static void deleteLastChars(StringBuilder sb, String s) {
        sb.delete(sb.length() - s.length(), sb.length());
    }
}
