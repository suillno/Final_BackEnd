package kr.co.kh.model.vo;

import lombok.*;


import java.math.BigDecimal;
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
    private Date createdAt;
    private String userName;
    private Long userId;

}
