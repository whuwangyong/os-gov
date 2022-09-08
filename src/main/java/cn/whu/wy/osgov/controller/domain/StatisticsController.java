package cn.whu.wy.osgov.controller.domain;

import cn.whu.wy.osgov.controller.RequestPath;
import cn.whu.wy.osgov.dto.response.ResponseEntity;
import cn.whu.wy.osgov.dto.statistics.ArtifactRiskDto;
import cn.whu.wy.osgov.dto.statistics.StatisticsDto;
import cn.whu.wy.osgov.service.StatisticsService;
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
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ResponseEntity get() {
        StatisticsDto statisticsDto = statisticsService.get();
        return ResponseEntity.success(statisticsDto);
    }

    /**
     * 返回按照风险系数排序，最危险的top-N个制品
     */
    @GetMapping("/top")
    public ResponseEntity top() {
        List<ArtifactRiskDto> top = statisticsService.top();
        return ResponseEntity.success(top);
    }


}
