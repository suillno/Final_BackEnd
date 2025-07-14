package kr.co.kh.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 리뷰 정보를 담는 VO (Value Object)
 */
@Data
public class ReviewVO {
    private Long reviewId;
    private String userName;
    private String gameTitle;
    private String content;
}
