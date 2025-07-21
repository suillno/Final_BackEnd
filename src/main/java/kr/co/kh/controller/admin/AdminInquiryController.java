package kr.co.kh.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
     * 전체 문의 목록을 조회합니다.
     * @return 전체 문의 리스트
     */
    @ApiOperation(value = "전체 문의 목록 조회", notes = "관리자가 전체 문의 내역을 확인합니다.")
    @GetMapping
    public ResponseEntity<List<InquiryVO>> getAll() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    /**
     * 특정 문의의 상세 정보를 조회합니다.
     * @param id 문의 ID
     * @return 문의 상세 정보
     */
    @ApiOperation(value = "단일 문의 상세 조회", notes = "문의 ID를 기준으로 상세 내용을 조회합니다.")
    @ApiImplicitParam(name = "id", value = "문의 ID", required = true, dataType = "long", paramType = "path")
    @GetMapping("/{id}")
    public ResponseEntity<InquiryVO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.getInquiry(id));
    }

    /**
     * 문의 상태를 변경합니다. (대기중 ↔ 처리중 ↔ 완료)
     * @param id 문의 ID
     * @param request 상태 변경 요청값 (예: {"status": "완료"})
     * @return 상태 변경 완료 메시지
     */
    @ApiOperation(value = "문의 상태 변경", notes = "문의 상태를 대기중, 처리중, 완료 중 하나로 변경합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "문의 ID", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "status", value = "변경할 상태 (대기중/처리중/완료)", required = true, dataType = "string", paramType = "body")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        inquiryService.updateStatus(id, status);
        return ResponseEntity.ok("상태 변경 완료");
    }
}