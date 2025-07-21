package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 고객 문의 VO 클래스
 * - INQUIRY 테이블과 매핑
 */
@Data
@ApiModel(description = "고객 문의 정보를 담는 VO (INQUIRY 테이블 매핑)")
public class InquiryVO {

    @ApiModelProperty(value = "문의 고유 ID", example = "1001")
    private Long inquiryId;

    @ApiModelProperty(value = "작성자 고유 ID", example = "501")
    private Long userId;

    @ApiModelProperty(value = "작성자 아이디", example = "john_doe")
    private String username;

    @ApiModelProperty(value = "문의 카테고리 또는 제목", example = "결제 관련 문의")
    private String category;

    @ApiModelProperty(value = "문의 내용", example = "지갑 충전이 되지 않습니다.")
    private String content;

    @ApiModelProperty(value = "처리 상태 (대기중, 처리중, 완료)", example = "대기중")
    private String status;

    @ApiModelProperty(value = "작성일", example = "2025-07-21T09:30:00")
    private Date createdAt;

    @ApiModelProperty(value = "수정일", example = "2025-07-22T13:00:00")
    private Date updatedAt;

    @ApiModelProperty(value = "관리자 답변", example = "결제 내역을 확인 중입니다.")
    private String answer;
}
