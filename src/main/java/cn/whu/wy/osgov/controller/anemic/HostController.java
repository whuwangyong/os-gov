package cn.whu.wy.osgov.controller.anemic;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.entity.Host;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYong
 * Date 2022/08/23
 * Time 15:53
 */
@RestController
@RequestMapping(RequestPath.HOST)
public class HostController extends BaseController<Host> {

    public HostController(BaseRepository<Host> repository) {
        super(repository);
    }
}
