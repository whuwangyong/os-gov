package cn.whu.wy.osgov.test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

/**
 * @Author WangYong
 * @Date 2022/12/07
 * @Time 14:28
 */
public class SomeTest {

    @Test
    public void testSplit() {
        System.out.println(Arrays.toString("aaa".split(":")));
    }

    @Test
    public void testUUID(){
        System.out.println(UUID.randomUUID().toString().length());
    }
}
