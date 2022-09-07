package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.AppHostDto;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.service.AppHostService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author WangYong
 * Date 2022/09/05
 * Time 21:06
 */
@RestController
@RequestMapping(RequestPath.APP_HOST)
public class AppHostController {

    private final AppHostService appHostService;

    public AppHostController(AppHostService appHostService) {
        this.appHostService = appHostService;
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Map<String, String> params) {
        String appName = params.get("appName");
        String ip = params.get("ip");
        List<AppHostDto> dtos;
        if (StringUtils.hasText(appName)) {
            dtos = appHostService.queryByAppName(appName);
        } else if (StringUtils.hasText(ip)) {
            dtos = appHostService.queryByIp(ip);
        } else {
            dtos = appHostService.get();
        }
        return ResponseEntity.success(dtos);
    }
}
