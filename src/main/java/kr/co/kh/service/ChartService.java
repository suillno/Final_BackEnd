package kr.co.kh.service;

import kr.co.kh.mapper.ChartMapper;
import kr.co.kh.model.vo.ChartVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartService {

    private final ChartMapper chartMapper;

    // 최근 7일 일별 방문자 수
    public List<ChartVO> getWeeklyVisitors() {
        return chartMapper.getWeeklyVisitors();
    }

    // 최근 7일 누적 방문자 수
    public int getTotalWeeklyVisitors() {
        return chartMapper.getTotalWeeklyVisitors();
    }

    // 전체 누적 방문자 수
    public int getAllVisitors() {
        return chartMapper.getAllVisitors();
    }

    // 최근 7일간 신규 가입자 수 조회
    public List<ChartVO> getWeeklySignups() {
        return chartMapper.getWeeklySignups();
    }

    public void insertVisitorLog(Long userId) {
        chartMapper.insertVisitorLog(userId);
    }

}
