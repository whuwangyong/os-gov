package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.ArtifactLicenseDto;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.service.ArtifactLicenseService;
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
 * Time 09:05
 */
@RestController
@RequestMapping(RequestPath.ARTIFACT_LICENSE)
public class ArtifactLicenseController {

    private final ArtifactLicenseService artifactLicenseService;

    public ArtifactLicenseController(ArtifactLicenseService artifactLicenseService) {
        this.artifactLicenseService = artifactLicenseService;
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Map<String, String> params) {
        String artifactName = params.get("artifactName");
        String licenseName = params.get("licenseName");
        List<ArtifactLicenseDto> dtos;
        if (StringUtils.hasText(licenseName)) {
            dtos = artifactLicenseService.queryByLicenseName(licenseName);
        } else if (StringUtils.hasText(artifactName)) {
            dtos = artifactLicenseService.queryByArtifactName(artifactName);
        } else {
            dtos = artifactLicenseService.get();
        }
        return ResponseEntity.success(dtos);
    }
}
