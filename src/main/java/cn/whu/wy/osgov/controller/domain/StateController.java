package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.dto.state.ArtifactRiskDto;
import cn.whu.wy.osgov.dto.state.StateDto;
import cn.whu.wy.osgov.service.StateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangYong
 * Date 2022/09/06
 * Time 09:31
 */
@RestController
@RequestMapping(RequestPath.STATE)
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity get() {
        StateDto stateDto = stateService.get();
        return ResponseEntity.success(stateDto);
    }

    /**
     * 返回按照风险系数排序，最危险的top-N个制品
     */
    @GetMapping("/top")
    public ResponseEntity top() {
        List<ArtifactRiskDto> top = stateService.top();
        return ResponseEntity.success(top);
    }


}
