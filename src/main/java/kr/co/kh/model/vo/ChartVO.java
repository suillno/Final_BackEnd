package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 방문자 수, 매출 등 공통 통계를 위한 차트 데이터 VO
 */
@Data
@ApiModel(description = "차트 데이터 VO - 통계 시리즈에 사용되는 공통 모델")
public class ChartVO {

    @ApiModelProperty(value = "차트 라벨 (요일, 월 등)", example = "월")
    private String label;

    @ApiModelProperty(value = "값 (방문자 수, 매출 등)", example = "1234.56")
    private Double value;
}
