package kr.co.kh.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 고객 문의 VO (Value Object)
 * INQUIRY 테이블과 매핑되는 Java 객체
 */
@Data
public class InquiryVO {
    private Long inquiryId;     // 문의 고유 ID (PK)
    private Long userId;        // 작성자 ID (USERS 테이블 FK)
    private Long gameId;        // 게임 ID (게임 테이블 ID 참조)
    private String gameTitle;   // 게임 제목 추가
    private String title;       // 문의 제목
    private String content;     // 문의 내용
    private String status;      // 처리 상태: 대기중, 처리중, 완료
    private Date createdAt;     // 작성 시각
    private Date updatedAt;     // 수정 시각
}
