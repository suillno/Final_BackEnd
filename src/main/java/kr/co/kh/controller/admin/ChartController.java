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

    /**
     * 최근 7일간 요일별 방문자 수를 조회합니다.
     * @return 각 요일별 방문자 수 리스트
     */
    @ApiOperation(value = "최근 7일 요일별 방문자 수", notes = "최근 일주일간의 방문자 수를 요일 기준으로 반환합니다.")
    @GetMapping("/visitors")
    public List<ChartVO> getWeeklyVisitors() {
        return chartService.getWeeklyVisitors();
    }

    /**
     * 7일간 총 방문자 수와 전체 누적 방문자 수를 함께 반환합니다.
     * @return 주간 방문자 수와 전체 방문자 수가 담긴 Map
     */
    @ApiOperation(value = "방문자 요약", notes = "최근 7일간 방문자 수와 전체 누적 방문자 수를 함께 반환합니다.")
    @GetMapping("/visitors/summary")
    public Map<String, Integer> getVisitorSummary() {
        Map<String, Integer> summary = new HashMap<>();
        summary.put("weeklyTotal", chartService.getTotalWeeklyVisitors());
        summary.put("allTimeTotal", chartService.getAllVisitors());
        return summary;
    }

    /**
     * 최근 7일간 요일별 회원 가입자 수를 조회합니다.
     * @return 요일별 회원 가입 수 리스트
     */
    @ApiOperation(value = "최근 7일간 신규 가입자 수", notes = "최근 일주일간 요일별 회원 가입자 수를 반환합니다.")
    @GetMapping("/signups")
    public List<ChartVO> getWeeklySignups() {
        return chartService.getWeeklySignups();
    }

    /**
     * 오늘의 매출 합계를 조회합니다.
     * @return 오늘 하루의 매출 금액 Map
     */
    @ApiOperation(value = "오늘의 매출", notes = "오늘 하루 발생한 총 매출 금액을 반환합니다.")
    @GetMapping("/revenue/today")
    public Map<String, Double> getTodayRevenue() {
        Map<String, Double> result = new HashMap<>();
        result.put("todayRevenue", chartService.getTodayRevenue());
        return result;
    }

    /**
     * 최근 7일간 일별 매출 데이터를 조회합니다.
     * @return 일자별 매출 데이터 리스트
     */
    @ApiOperation(value = "최근 7일간 일별 매출", notes = "최근 일주일간의 일별 매출 데이터를 반환합니다.")
    @GetMapping("/revenue/weekly")
    public List<ChartVO> getWeeklyRevenue() {
        return chartService.getWeeklyRevenue();
    }
}