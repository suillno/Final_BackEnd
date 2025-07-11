package kr.co.kh.model.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Data
@Slf4j
public class GameLikeVO {
    private Long likeId;
    private String userName;
    private Long gameId;
    private String title;
    private String backgroundImage;
    private int price;
    private int salePrice;
    private String released;
    private String esrbRating;
    private String cartType;
    private String result;

    private int discountSalePrice;


    /** 생성 일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp createdAt;

}
