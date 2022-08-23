package cn.whu.wy.osgov.controller.anemic;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.entity.Tag;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:52
 */
@RestController
@RequestMapping(RequestPath.TAG)
public class TagController extends BaseController<Tag> {

    public TagController(BaseRepository<Tag> repository) {
        super(repository);
    }
}
