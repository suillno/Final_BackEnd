package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * 장바구니 항목 정보를 담는 VO (Value Object) 클래스
 * 클라이언트로부터 전달받은 장바구니 관련 정보를 담아 컨트롤러 → 서비스 계층으로 전달
 */
@Data
@Slf4j
@ApiModel(description = "장바구니 또는 찜 항목 정보를 담는 VO")
public class GameCartVO {

    @ApiModelProperty(value = "사용자 아이디", example = "testUser")
    private String userName;

    @ApiModelProperty(value = "장바구니 고유 ID", example = "101")
    private Long cartId;

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

    @ApiModelProperty(value = "구분 타입 (CART: 장바구니, LIKE: 찜)", example = "CART")
    private String cartType;

    @ApiModelProperty(value = "추가된 시점", example = "2025-07-21 10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @ApiModelProperty(value = "동작 타입 (프론트 요청 구분용)", example = "1")
    private int actionType;

    @ApiModelProperty(value = "구매 여부", example = "false")
    private boolean purchase;

    @ApiModelProperty(value = "결과 메시지 또는 상태", example = "SUCCESS")
    private String result;
}
