package kr.co.kh.model.vo;

import lombok.Data;

import java.util.Date;


/**
 * 고객 문의 VO
 * INQUIRY 테이블과 매핑됨
 */
@Data
public class InquiryVO {
    private Long inquiryId;   // 문의 고유 ID
    private Long userId;      // 작성자 고유 ID
    private String username;  // 작성자 아이디
    private String category;  // 문의 유형 or 제목
    private String content;   // 문의 내용
    private String status;    // 처리 상태 (대기중, 처리중, 완료)
    private Date createdAt;   // 작성일
    private Date updatedAt;   // 수정일
}
