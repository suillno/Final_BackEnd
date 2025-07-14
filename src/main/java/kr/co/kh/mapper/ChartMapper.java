package kr.co.kh.mapper;

import kr.co.kh.model.vo.ChartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChartMapper {

    //  일별 방문자 수 (최근 7일) 1
    List<ChartVO> getWeeklyVisitors();

    //  최근 7일 총 방문자 수
    int getTotalWeeklyVisitors();

    //  전체 누적 방문자 수
    int getAllVisitors();

    // 신규 가입자 수 (최근 7일)
    List<ChartVO> getWeeklySignups();

    void insertVisitorLog(Long userId);

}
