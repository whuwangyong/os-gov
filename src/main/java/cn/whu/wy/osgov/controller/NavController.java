package cn.whu.wy.osgov.controller;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author WangYong
 * Date 2022/09/09
 * Time 10:33
 */
@RestController
@RequestMapping("/api/nav")
public class NavController {

    @GetMapping
    public Object nav() throws IOException, ParseException {
        Resource resource = new ClassPathResource("static/nav.json");
        InputStream inputStream = resource.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        return new JSONParser().parse(bufferedReader);
    }
}
