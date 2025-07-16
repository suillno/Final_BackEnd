package kr.co.kh.service;

import kr.co.kh.mapper.InquiryMapper;
import kr.co.kh.model.vo.InquiryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고객 문의 서비스 클래스
 * - 컨트롤러 ↔ Mapper 사이의 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    /**
     * 고객 문의 등록
     * - 상태값이 없으면 '대기중'으로 기본 설정
     */
    public void saveInquiry(InquiryVO inquiry) {
        if (inquiry.getStatus() == null || inquiry.getStatus().trim().isEmpty()) {
            inquiry.setStatus("대기중");
        }
        inquiryMapper.insertInquiry(inquiry);
    }

    /**
     * 전체 문의 목록 조회 (관리자)
     */
    public List<InquiryVO> getAllInquiries() {
        return inquiryMapper.findAllInquiries();
    }

    /**
     * 단일 문의 상세 조회
     */
    public InquiryVO getInquiry(Long inquiryId) {
        return inquiryMapper.findInquiryById(inquiryId);
    }

    /**
     * 문의 상태 변경 처리 (대기중 → 처리중 → 완료 등)
     */
    public void updateStatus(Long inquiryId, String status) {
        inquiryMapper.updateInquiryStatus(inquiryId, status);
    }

    /**
     * 문의 답변 저장 (관리자)
     */
    public void saveAnswer(Long inquiryId, String answer) {
        inquiryMapper.updateInquiryAnswer(inquiryId, answer);
    }

    /**
     *  문의 단건 삭제
     */
    public void deleteInquiry(Long inquiryId) {
        inquiryMapper.deleteInquiryById(inquiryId);
    }
}
