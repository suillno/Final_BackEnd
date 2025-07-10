package kr.co.kh.model.vo;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class GameWalletVO {
    private Long logId;
    private Long walletId;
    private String logType;
    private Long amount;
    private String description;

}
