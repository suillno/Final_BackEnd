package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * 대시보드에서 사용하는 게임 통계 및 잔액, 생성일 등 정보를 담는 VO
 */
@Data // Lombok 어노테이션: getter/setter, toString, equals, hashCode 자동 생성
@Slf4j // Lombok 어노테이션: 로깅을 위한 Logger 필드 자동 생성
@ApiModel(description = "대시보드 데이터 VO - 게임 개수, 잔액, 생성일 등 정보 포함")
public class DashBoardVO {

    @ApiModelProperty(value = "게임 제목 또는 이름", example = "엘든 링")
    private String title;

    @ApiModelProperty(value = "생성 일시", example = "2025-07-21T10:30:00.000")
    private Timestamp createdAt;

    @ApiModelProperty(value = "보유 잔액", example = "12000")
    private Long balance;

    @ApiModelProperty(value = "게임 보유 개수", example = "15")
    private int gameCount;
}
