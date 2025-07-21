package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Data
@Slf4j
@ApiModel(description = "사용자의 찜(좋아요) 게임 정보를 담는 VO")
public class GameLikeVO {

    @ApiModelProperty(value = "찜 ID", example = "1")
    private Long likeId;

    @ApiModelProperty(value = "사용자 이름", example = "user123")
    private String userName;

    @ApiModelProperty(value = "게임 ID", example = "101")
    private Long gameId;

    @ApiModelProperty(value = "게임 제목", example = "Cyberpunk 2077")
    private String title;

    @ApiModelProperty(value = "게임 배경 이미지 URL", example = "https://cdn.example.com/image.jpg")
    private String backgroundImage;

    @ApiModelProperty(value = "원래 가격", example = "59000")
    private int price;

    @ApiModelProperty(value = "할인 가격", example = "39000")
    private int salePrice;

    @ApiModelProperty(value = "출시일", example = "2023-10-05")
    private String released;

    @ApiModelProperty(value = "ESRB 등급", example = "Mature")
    private String esrbRating;

    @ApiModelProperty(value = "카트 타입 (CART / LIKE)", example = "LIKE")
    private String cartType;

    @ApiModelProperty(value = "처리 결과 메시지", example = "찜 등록 완료")
    private String result;

    @ApiModelProperty(value = "할인 적용 후 최종 가격", example = "35000")
    private int discountSalePrice;

    @ApiModelProperty(value = "생성 일시", example = "2025-07-21 10:15:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;
}
