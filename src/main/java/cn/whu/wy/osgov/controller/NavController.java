package cn.whu.wy.osgov.controller;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        File file = resource.getFile();
        return new JSONParser().parse(new FileReader(file));
    }
}
