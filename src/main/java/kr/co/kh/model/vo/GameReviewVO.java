package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel(description = "게임 리뷰 정보를 담는 VO")
public class GameReviewVO {

    @ApiModelProperty(value = "리뷰 ID", example = "1")
    private Long reviewId;

    @ApiModelProperty(value = "작성자 아이디", example = "user123")
    private String userName;

    @ApiModelProperty(value = "게임 ID", example = "1001")
    private Long gameId;

    @ApiModelProperty(value = "게임 제목", example = "Elden Ring")
    private String title;

    @ApiModelProperty(value = "리뷰 별점", example = "4.5")
    private Double rating;

    @ApiModelProperty(value = "리뷰 내용", example = "정말 몰입감 있는 게임이었습니다.")
    private String content;

    @ApiModelProperty(value = "리뷰 작성일시", example = "2025-07-21 10:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @ApiModelProperty(value = "리뷰 수정일시", example = "2025-07-22 11:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp updatedAt;
}
