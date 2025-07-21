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
@ApiModel(description = "지갑 정보 VO - 유저의 지갑 상태 및 잔액 정보")
public class GameWalletVO {

    @ApiModelProperty(value = "지갑 고유 ID", example = "10")
    private Long walletId;

    @ApiModelProperty(value = "유저 고유 ID", example = "101")
    private Long userId;

    @ApiModelProperty(value = "유저 이름", example = "user123")
    private String userName;

    @ApiModelProperty(value = "로그 타입 (1: 충전, 2: 사용)", example = "1")
    private int logType;

    @ApiModelProperty(value = "현재 지갑 잔액", example = "15000")
    private Long balance;

    @ApiModelProperty(value = "지갑 생성 일시", example = "2025-07-21T09:30:00")
    private Date createdAt;

    @ApiModelProperty(value = "처리 결과 메시지", example = "충전 성공")
    private String result;
}
