package kr.co.kh.controller.admin;

import io.swagger.annotations.ApiOperation;
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

    // [1] 최근 7일 요일별 방문자 수 1
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

    // [3] 7일간 회원가입 수
    @ApiOperation(value = "최근 7일간 신규 가입자 수", notes = "요일별 회원 가입자 수를 반환합니다.")
    @GetMapping("/signups")
    public List<ChartVO> getWeeklySignups() {
        return chartService.getWeeklySignups();
    }

    // 4. 오늘 매출 합계
    @GetMapping("/revenue/today")
    public Map<String, Double> getTodayRevenue() {
        Map<String, Double> result = new HashMap<>();
        result.put("todayRevenue", chartService.getTodayRevenue());
        return result;
    }


    // 5. 최근 7일 일별 매출
    @GetMapping("/revenue/weekly")
    public List<ChartVO> getWeeklyRevenue() {
        return chartService.getWeeklyRevenue();
    }


}
