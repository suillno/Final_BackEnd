package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Data
@Slf4j
@ApiModel(description = "공동구매 할인 정보를 담는 VO")
public class GameDiscountVO {

    @ApiModelProperty(value = "할인 고유 ID", example = "1")
    private Long discountId;

    @ApiModelProperty(value = "사용자 아이디", example = "testUser")
    private String userName;

    @ApiModelProperty(value = "게임 ID", example = "12345")
    private Long gameId;

    @ApiModelProperty(value = "게임 제목", example = "엘든 링")
    private String title;

    @ApiModelProperty(value = "게임 배경 이미지 URL", example = "https://cdn.example.com/game.jpg")
    private String backgroundImage;

    @ApiModelProperty(value = "정가", example = "59000")
    private int price;

    @ApiModelProperty(value = "할인가", example = "39000")
    private int salePrice;

    @ApiModelProperty(value = "출시일", example = "2022-02-25")
    private String released;

    @ApiModelProperty(value = "이용 등급", example = "청소년 이용불가")
    private String esrbRating;

    @ApiModelProperty(value = "할인율", example = "34")
    private Long discountPercent;

    @ApiModelProperty(value = "공동구매 처리 결과", example = "SUCCESS")
    private String result;

    @ApiModelProperty(value = "신청자 수", example = "5")
    private int countApplicants;

    @ApiModelProperty(value = "활성 여부 (1: 활성, 0: 비활성)", example = "1")
    private int isActive;

    @ApiModelProperty(value = "공동구매 신청자 목록 (user1,user2,...)", example = "user1,user2,user3")
    private String applicantList;

    @ApiModelProperty(value = "등록 시각", example = "2025-07-21 11:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;
}
