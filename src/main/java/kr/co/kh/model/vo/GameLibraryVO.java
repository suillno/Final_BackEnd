package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Data
@Slf4j
@ApiModel(description = "사용자의 게임 라이브러리 정보를 담는 VO")
public class GameLibraryVO {

    @ApiModelProperty(value = "라이브러리 고유 ID", example = "1")
    private Long libraryId;

    @ApiModelProperty(value = "사용자 아이디", example = "user123")
    private String userName;

    @ApiModelProperty(value = "게임 ID", example = "101")
    private Long gameId;

    @ApiModelProperty(value = "게임 제목", example = "The Witcher 3")
    private String title;

    @ApiModelProperty(value = "게임 배경 이미지 URL", example = "https://cdn.example.com/image.jpg")
    private String backgroundImage;

    @ApiModelProperty(value = "구매 가격", example = "29900")
    private Long price;

    @ApiModelProperty(value = "게임 등록일시", example = "2025-07-21 09:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

    @ApiModelProperty(value = "게임 출시일", example = "2023-12-15")
    private String released;

    @ApiModelProperty(value = "ESRB 등급", example = "Mature")
    private String esrRating;

    @ApiModelProperty(value = "처리 결과 메시지", example = "게임이 라이브러리에 추가되었습니다.")
    private String result;
}
