package cn.whu.wy.osgov.controller;

import cn.whu.wy.osgov.entity.Software;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYong
 * Date 2022/08/13
 * Time 15:14
 */
@RestController
@RequestMapping("/api/software")
public class SoftwareController extends BaseController<Software> {

    @Autowired
    public SoftwareController(BaseRepository<Software> repository) {
        super(repository);
    }
}
