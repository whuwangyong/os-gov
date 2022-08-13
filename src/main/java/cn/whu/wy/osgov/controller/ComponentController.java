package cn.whu.wy.osgov.controller;

import cn.whu.wy.osgov.entity.Component;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYong
 * Date 2022/08/11
 * Time 09:38
 */
@RestController
@RequestMapping("/api/components")
public class ComponentController extends BaseController<Component> {

    @Autowired
    public ComponentController(BaseRepository<Component> repository) {
        super(repository);
    }
}