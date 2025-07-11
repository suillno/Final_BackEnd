package kr.co.kh.model.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class GameWalletVO {
    private Long walletId;
    private Long userId;
    private String userName;
    private int logType;
    private Long balance;
    private Date createdAt;
    private String result;


}
