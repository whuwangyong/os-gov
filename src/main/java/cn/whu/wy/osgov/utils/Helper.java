package cn.whu.wy.osgov.utils;

import cn.whu.wy.osgov.entity.Entity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 辅助工具类
 *
 * @author WangYong
 * Date 2022/05/06
 * Time 16:25
 */
public class Helper {

    /**
     * 属性拷贝
     *
     * @param src
     * @param dst
     * @param excludeNull true：只拷贝有值的字段
     */
    public static void copyProperties(Object src, Object dst, boolean excludeNull) {
        if (excludeNull) {
            BeanUtils.copyProperties(src, dst, getNullProperties(src));
        } else {
            BeanUtils.copyProperties(src, dst);
        }
    }

    private static String[] getNullProperties(Object src) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(src);
        return Stream.of(beanWrapper.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> beanWrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static Map<String, Object> getFieldsValue(Object o) {
        Map<String, Object> map = new HashMap<>();
        Class<?> aClass = o.getClass();
        while (aClass != Object.class) {
            Field[] allFields = aClass.getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                map.put(field.getName(), value);
            }
            aClass = aClass.getSuperclass();
        }
        return map;
    }

    public static <T extends Entity> Object newInstance(T t) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends Entity> aClass = t.getClass();
        return aClass.getConstructor().newInstance();
    }
}
