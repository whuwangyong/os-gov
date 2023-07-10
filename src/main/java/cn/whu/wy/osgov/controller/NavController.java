package cn.whu.wy.osgov.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author WangYong
 * @Date 2022/09/09
 * @Time 14:42
 */
@RestController
@RequestMapping(RequestPath.NAV)
public class NavController {

    private static String NAV_JSON;

    static {
        try {
            ClassPathResource resource = new ClassPathResource("static/nav.json");
            InputStream inputStream = resource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            NAV_JSON = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping
    public Object nav() throws IOException {
        return NAV_JSON;
    }
}
