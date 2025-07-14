package kr.co.kh.mapper;

import kr.co.kh.model.vo.InquiryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 고객 문의 Mapper 인터페이스
 * - DB와 직접 연결되어 쿼리 실행
 */
@Mapper
public interface InquiryMapper {

    // 고객 문의 등록
    void insertInquiry(InquiryVO inquiry);

    // 전체 문의 목록 조회 (관리자용)
    List<InquiryVO> findAllInquiries();

    // 특정 문의 조회
    InquiryVO findInquiryById(Long inquiryId);

    // 문의 상태 변경 (대기중, 처리중, 완료)
    void updateInquiryStatus(@Param("inquiryId") Long inquiryId, @Param("status") String status);
}
