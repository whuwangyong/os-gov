package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.ArtifactTagDto;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.service.ArtifactTagService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 09:20
 */
@RestController
@RequestMapping(RequestPath.ARTIFACT_TAG)
public class ArtifactTagController {

    private final ArtifactTagService artifactTagService;

    public ArtifactTagController(ArtifactTagService artifactTagService) {
        this.artifactTagService = artifactTagService;
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Map<String, String> params) {
        String artifactName = params.get("artifactName");
        String tagName = params.get("tagName");
        List<ArtifactTagDto> dtos;
        if (StringUtils.hasText(tagName)) {
            dtos = artifactTagService.queryByTagName(tagName);
        } else if (StringUtils.hasText(artifactName)) {
            dtos = artifactTagService.queryByArtifactName(artifactName);
        } else {
            dtos = artifactTagService.get();
        }
        return ResponseEntity.success(dtos);
    }
}
