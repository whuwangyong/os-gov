package cn.whu.wy.osgov.test;

import cn.whu.wy.osgov.entity.Component;
import cn.whu.wy.osgov.utils.Helper;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author WangYong
 * Date 2022/08/14
 * Time 14:08
 */
public class HelperTest {

    @Test
    public void testGetFieldsValue() throws IllegalAccessException {
        Component kafka = Component.builder().groupId("org.apache").artifactId("kafka").version("2.8.0").build();
        kafka.setId(1);
        Map<String, Object> fieldsValue = Helper.getFieldsValue(kafka);
        assert fieldsValue.get("groupId").equals("org.apache");
        assert fieldsValue.get("artifactId").equals("kafka");
        assert fieldsValue.get("version").equals("2.8.0");
        assert fieldsValue.get("id").equals(1);
    }
}
