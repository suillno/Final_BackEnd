package kr.co.kh.mapper;

import kr.co.kh.model.vo.InquiryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 고객 문의 Mapper
 * MyBatis와 연결되는 매퍼 인터페이스
 */
@Mapper
public interface InquiryMapper {

    // 고객 문의 등록
    void insertInquiry(InquiryVO inquiry);

    // 전체 문의 목록 조회
    List<InquiryVO> findAllInquiries();

    // 특정 문의 조회
    InquiryVO findInquiryById(Long inquiryId);
}
