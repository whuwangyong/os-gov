package cn.whu.wy.osgov.controller;

import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.entity.Entity;
import cn.whu.wy.osgov.repository.BaseRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> p = params.entrySet().stream()
                .filter(map -> StringUtils.hasText(map.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        String pageSize = p.remove("pageSize");
        String page = p.remove("page");
        List<T> list;

        // 除页码外还有别的参数，根据条件获取数据。不分页
        if (p.size() > 0) {
            list = repository.query(p);
            return ResponseEntity.success(list);
        } else if (pageSize != null && page != null) {
            // 指定页码时，按照页码获取数据
            list = repository.get(Integer.parseInt(pageSize), Integer.parseInt(page));
            return ResponseEntity.success(list, repository.count());
        } else {
            return ResponseEntity.fail("参数错误:" + params);
        }
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
