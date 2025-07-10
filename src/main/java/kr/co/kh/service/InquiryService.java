package kr.co.kh.service;

import kr.co.kh.mapper.InquiryMapper;
import kr.co.kh.model.vo.InquiryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고객 문의 서비스 클래스
 * 비즈니스 로직 처리 담당
 */
@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    /**
     * 고객 문의 저장
     * 상태가 없다면 기본값 '대기중'으로 설정
     */
    public void saveInquiry(InquiryVO inquiry) {
        if (inquiry.getStatus() == null || inquiry.getStatus().trim().isEmpty()) {
            inquiry.setStatus("대기중");
        }
        inquiryMapper.insertInquiry(inquiry);
    }


    /**
     * 전체 문의 목록 조회
     */
    public List<InquiryVO> getAllInquiries() {
        return inquiryMapper.findAllInquiries();
    }

    /**
     * 특정 문의 상세 조회
     */
    public InquiryVO getInquiry(Long inquiryId) {
        return inquiryMapper.findInquiryById(inquiryId);
    }
}
