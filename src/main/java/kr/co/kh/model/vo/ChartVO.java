package kr.co.kh.model.vo;

import lombok.Data;

// 차트 공통 VO
@Data
public class ChartVO {
    private String label;    // 요일, 월 등 (ex. 월, 화, 1월, 2월 등)
    private Integer value;   // 카운트 값 (매출, 방문자 수 등)
}
