package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.ArtifactAppDto;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.service.ArtifactAppService;
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
 * Time 22:26
 */
@RestController
@RequestMapping(RequestPath.ARTIFACT_APP)
public class ArtifactAppController {

    private final ArtifactAppService artifactAppService;

    public ArtifactAppController(ArtifactAppService artifactAppService) {
        this.artifactAppService = artifactAppService;
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Map<String, String> params) {
        String artifactName = params.get("artifactName");
        String appName = params.get("appName");
        List<ArtifactAppDto> dtos;
        if (StringUtils.hasText(appName)) {
            dtos = artifactAppService.queryByAppName(appName);
        } else if (StringUtils.hasText(artifactName)) {
            dtos = artifactAppService.queryByArtifactName(artifactName);
        } else {
            dtos = artifactAppService.get();
        }
        return ResponseEntity.success(dtos);
    }
}
