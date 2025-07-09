package kr.co.kh.service;

import kr.co.kh.mapper.InquiryMapper;
import kr.co.kh.model.vo.InquiryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고객 문의 서비스 클래스
 * 비즈니스 로직을 처리함
 */
@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    /**
     * 고객 문의 저장
     * @param inquiry 사용자 입력 정보
     */
    public void saveInquiry(InquiryVO inquiry) {
        // 상태 값이 없으면 기본값 '대기중' 설정
        if (inquiry.getStatus() == null || inquiry.getStatus().trim().isEmpty()) {
            inquiry.setStatus("대기중");
        }
        inquiryMapper.insertInquiry(inquiry);
    }

    /**
     * 모든 문의 목록 조회
     * @return 문의 목록
     */
    public List<InquiryVO> getAllInquiries() {
        return inquiryMapper.findAllInquiries();
    }

    /**
     * 특정 문의 조회
     * @param inquiryId 문의 ID
     * @return 문의 정보
     */
    public InquiryVO getInquiry(Long inquiryId) {
        return inquiryMapper.findInquiryById(inquiryId);
    }
}
