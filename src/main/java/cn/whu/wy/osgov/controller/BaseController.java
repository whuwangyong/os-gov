package cn.whu.wy.osgov.controller;

import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.entity.Entity;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author WangYong
 * Date 2022/08/11
 * Time 09:38
 */
public class BaseController<T extends Entity> {

    protected final BaseRepository<T> repository;


    public BaseController(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity add(@RequestBody T t) {
        repository.add(t);
        return ResponseEntity.success();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable int id) {
        T byId = repository.findById(id);
        return ResponseEntity.success(byId);
    }

    @GetMapping
    public ResponseEntity get(@RequestParam Map<String, String> params) {
        String pageSize = params.remove("pageSize");
        String page = params.remove("page");
        List<T> list;
        if (pageSize != null && page != null) {
            // 指定页码时，按照页码获取数据
            list = repository.get(Integer.parseInt(pageSize), Integer.parseInt(page));
        } else {
            // 未指定页码时，根据条件获取数据
            list = repository.query(params);
        }
        return ResponseEntity.success(list, repository.count());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        repository.delete(id);
        return ResponseEntity.success();
    }


    @PutMapping
    public ResponseEntity update(@RequestBody T newObj) {
        repository.update(newObj);
        return ResponseEntity.success();
    }


}
