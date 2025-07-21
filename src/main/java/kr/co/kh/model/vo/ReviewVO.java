package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 리뷰 정보를 담는 VO (Value Object)
 */
@Data
@ApiModel(description = "게임 리뷰 정보를 담는 VO")
public class ReviewVO {

    @ApiModelProperty(value = "리뷰 고유 ID", example = "101")
    private Long reviewId;

    @ApiModelProperty(value = "작성자 아이디", example = "john_doe")
    private String userName;

    @ApiModelProperty(value = "게임 제목", example = "The Witcher 3")
    private String gameTitle;

    @ApiModelProperty(value = "리뷰 내용", example = "정말 재미있고 몰입감 있는 게임입니다!")
    private String content;
}
