package cn.whu.wy.osgov.controller.anemic;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.entity.Artifact;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYong
 * Date 2022/08/11
 * Time 09:38
 */
@RestController
@RequestMapping(RequestPath.ARTIFACT)
public class ArtifactController extends BaseController<Artifact> {

    public ArtifactController(BaseRepository<Artifact> repository) {
        super(repository);
    }
}
