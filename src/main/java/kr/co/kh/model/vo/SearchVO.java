package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "방문자 및 통계 데이터를 담는 VO")
public class SearchVO {

    @ApiModelProperty(value = "고유 ID", example = "1")
    private int id;

    @ApiModelProperty(value = "통계 일자", example = "2024-07-21")
    private Date statDate;

    @ApiModelProperty(value = "통계 시간", example = "15:00")
    private String statTime;

    @ApiModelProperty(value = "조회수 또는 통계 수치", example = "230")
    private int count;

    @ApiModelProperty(value = "지역 이름", example = "서울특별시")
    private String areaName;

    @ApiModelProperty(value = "방문자 수", example = "150")
    private int visitorCount;

    @ApiModelProperty(value = "방문자 이름", example = "홍길동")
    private String visitorName;
}
