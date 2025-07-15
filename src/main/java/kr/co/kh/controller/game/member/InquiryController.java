package kr.co.kh.controller.game.member;

import kr.co.kh.model.vo.InquiryVO;
import kr.co.kh.service.InquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 고객 문의 컨트롤러 (회원용)
 * URL prefix: /game/member/inquiry
 */
@RestController
@RequestMapping("/game/member/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    /**
     * 문의 등록 API
     * @param inquiry 사용자 요청 (userId, category, content)
     */
    @PostMapping("/submit")
    public ResponseEntity<String> submitInquiry(@RequestBody InquiryVO inquiry) {
        inquiryService.saveInquiry(inquiry);
        return ResponseEntity.ok("문의가 정상적으로 등록되었습니다.");
    }


    /**
     * 전체 문의 목록 조회 (관리자용 등)
     */
    @GetMapping
    public ResponseEntity<List<InquiryVO>> getAll() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    /**
     * 특정 문의 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<InquiryVO> getInquiry(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.getInquiry(id));
    }


    /**
     * 특정 문의 삭제 (관리자용)
     * - 프런트에서 DELETE 요청 전송 시 사용
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok("문의가 삭제되었습니다.");
    }
}
