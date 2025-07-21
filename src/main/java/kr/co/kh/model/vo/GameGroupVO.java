package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@ApiModel(description = "공동구매 요청/응답 정보를 담는 VO")
public class GameGroupVO {

    @ApiModelProperty(value = "사용자 아이디", example = "testUser")
    private String userName;

    @ApiModelProperty(value = "게임 ID", example = "1001")
    private Long gameId;

    @ApiModelProperty(value = "요청 처리 결과 메시지", example = "공동구매 신청 완료")
    private String result;
}
