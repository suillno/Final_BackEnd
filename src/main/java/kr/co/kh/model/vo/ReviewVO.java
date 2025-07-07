package kr.co.kh.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 리뷰 정보를 담는 VO (Value Object)
 */
@Getter
@Setter
public class ReviewVO {

    private Long reviewId;     // REVIEW_ID
    private String userName;   // REVIEW.USER_NAME
    private String gameTitle;  // GAME_LIKE.TITLE
    private String content;    // REVIEW.CONTENT
}
