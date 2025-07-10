package kr.co.kh.model.vo;

import lombok.*;


import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class GameWalletLogVO {
    private Long walletId;
    private Long userId;
    private String userName;
    private Long balance;
    private Date createdAt;
    private Date updatedAt;
}
