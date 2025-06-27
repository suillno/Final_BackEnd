package kr.co.kh.model.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * 장바구니 항목 정보를 담는 VO (Value Object) 클래스
 * 클라이언트로부터 전달받은 장바구니 관련 정보를 담아 컨트롤러 → 서비스 계층으로 전달
 */
@Data // Lombok 어노테이션: getter/setter, toString, equals, hashCode 자동 생성
@Slf4j // Lombok 어노테이션: 로깅을 위한 Logger 필드 자동 생성
public class GameReviewVO {


    /** 장바구니 고유 ID (DB 자동 생성) */
    private Long reviewId;

    /** 사용자 아이디 (로그인된 사용자 정보) */
    private String userName;

    /** 게임 ID */
    private Long gameId;

    // 평점
    private Long rating;

    // 내용
    private String content;

    // 등록일
    private Timestamp createdAt;
}
