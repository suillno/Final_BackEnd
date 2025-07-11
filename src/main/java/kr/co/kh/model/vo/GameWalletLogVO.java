package kr.co.kh.model.vo;

import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class GameWalletLogVO {
    private Long logId;
    private Long logType;
    private Long amount;
    private LocalDateTime createdAt;
    private String userName;
    private Long userId;
    private String logText;
    private Long balance;


}
