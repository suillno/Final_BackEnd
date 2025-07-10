package kr.co.kh.controller.admin;

import kr.co.kh.model.vo.ChartVO;
import kr.co.kh.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/game/admin/chart")
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    // [1] 최근 7일 요일별 방문자 수
    @GetMapping("/visitors")
    public List<ChartVO> getWeeklyVisitors() {
        return chartService.getWeeklyVisitors();
    }

    // [2] 7일간 총 방문자 수 + 전체 누적 수 한 번에 반환
    @GetMapping("/visitors/summary")
    public Map<String, Integer> getVisitorSummary() {
        Map<String, Integer> summary = new HashMap<>();
        summary.put("weeklyTotal", chartService.getTotalWeeklyVisitors());
        summary.put("allTimeTotal", chartService.getAllVisitors());
        return summary;
    }
}
