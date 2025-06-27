package kr.co.kh.model.vo;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

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

}
