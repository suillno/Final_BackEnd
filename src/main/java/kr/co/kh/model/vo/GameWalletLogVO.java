package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "지갑 거래 로그 VO - 충전/사용 로그 정보")
public class GameWalletLogVO {

    @ApiModelProperty(value = "거래 로그 고유 ID", example = "101")
    private Long logId;

    @ApiModelProperty(value = "로그 타입 (1: 충전, 2: 사용)", example = "1")
    private Long logType;

    @ApiModelProperty(value = "거래 금액", example = "5000")
    private Long amount;

    @ApiModelProperty(value = "거래 일시", example = "2025-07-21T10:20:30")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "유저 이름", example = "user123")
    private String userName;

    @ApiModelProperty(value = "유저 고유 ID", example = "55")
    private Long userId;

    @ApiModelProperty(value = "로그 설명 텍스트", example = "지갑 충전")
    private String logText;

    @ApiModelProperty(value = "거래 후 잔액", example = "10000")
    private Long balance;
}
