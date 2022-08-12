package cn.whu.wy.osgov.controller;

import cn.whu.wy.osgov.dto.response.ResponseCode;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.entity.Component;
import cn.whu.wy.osgov.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangYong
 * Date 2022/08/11
 * Time 09:38
 */
@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private ComponentRepository repository;

    @Autowired
    public ComponentController(ComponentRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody Component component){
        repository.add(component);
        return new ResponseEntity(ResponseCode.SUCCESS);
    }

    @GetMapping
    public ResponseEntity getById(@RequestParam int id){
        Component byId = repository.findById(id);
        return new ResponseEntity(ResponseCode.SUCCESS,)


    }




}
