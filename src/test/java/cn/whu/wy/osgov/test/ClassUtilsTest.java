package cn.whu.wy.osgov.test;

import cn.whu.wy.osgov.entity.Artifact;
import cn.whu.wy.osgov.utils.ClassUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author WangYong
 * Date 2022/08/14
 * Time 14:08
 */
public class ClassUtilsTest {

    @Test
    public void testGetFieldsValue() {
        Artifact kafka = Artifact.builder().org("org.apache").name("kafka").version("2.8.0").build();
        kafka.setId(1);
        Map<String, Object> fieldsValue = ClassUtils.getFieldsValue(kafka);
        assert fieldsValue.get("org").equals("org.apache");
        assert fieldsValue.get("name").equals("kafka");
        assert fieldsValue.get("version").equals("2.8.0");
        assert fieldsValue.get("id").equals(1);
    }
}
