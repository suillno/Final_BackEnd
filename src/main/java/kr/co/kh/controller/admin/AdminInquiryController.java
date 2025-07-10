package kr.co.kh.controller.admin;

import kr.co.kh.model.vo.InquiryVO;
import kr.co.kh.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 관리자용 고객 문의 관리 컨트롤러
 * - 접수 내역 확인 및 상태 변경 가능
 * - prefix: /game/admin/inquiry
 */
@RestController
@RequestMapping("/game/admin/inquiry")
@RequiredArgsConstructor
public class AdminInquiryController {

    private final InquiryService inquiryService;

    /**
     * 전체 문의 목록 조회
     * - 유저명, 카테고리, 상태 포함
     */
    @GetMapping
    public ResponseEntity<List<InquiryVO>> getAll() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    /**
     * 단일 문의 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<InquiryVO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.getInquiry(id));
    }

    /**
     * 문의 상태 변경 (대기중 ↔ 처리중 ↔ 완료)
     * - 요청 바디에 { "status": "완료" } 형태로 전달받음
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        inquiryService.updateStatus(id, status);
        return ResponseEntity.ok("상태 변경 완료");
    }
}
