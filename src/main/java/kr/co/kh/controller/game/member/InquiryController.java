package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.InquiryVO;
import kr.co.kh.service.InquiryService;
import lombok.RequiredArgsConstructor;
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
     * 사용자의 문의를 등록합니다.
     * @param inquiry 사용자 문의 정보(userId, category, content 등)
     * @return 등록 완료 메시지
     */
    @ApiOperation(value = "문의 등록", notes = "사용자가 문의 내용을 제출합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inquiry", value = "문의 정보", required = true, dataType = "InquiryVO", paramType = "body")
    })
    @PostMapping("/submit")
    public ResponseEntity<String> submitInquiry(@RequestBody InquiryVO inquiry) {
        inquiryService.saveInquiry(inquiry);
        return ResponseEntity.ok("문의가 정상적으로 등록되었습니다.");
    }

    /**
     * 전체 문의 목록을 조회합니다.
     * @return 모든 문의 리스트
     */
    @ApiOperation(value = "전체 문의 목록 조회", notes = "등록된 모든 문의 목록을 반환합니다.")
    @GetMapping
    public ResponseEntity<List<InquiryVO>> getAll() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    /**
     * 특정 문의의 상세 정보를 조회합니다.
     * @param id 문의 ID
     * @return 문의 상세 정보
     */
    @ApiOperation(value = "문의 상세 조회", notes = "문의 ID를 통해 상세 정보를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "문의 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InquiryVO> getInquiry(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.getInquiry(id));
    }

    /**
     * 특정 문의에 대한 답변을 등록 또는 수정합니다.
     * @param id 문의 ID
     * @param answer 관리자 답변 내용
     * @return 답변 등록 완료 메시지
     */
    @ApiOperation(value = "문의 답변 등록", notes = "문의 ID에 대한 답변을 등록하거나 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "문의 ID", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "answer", value = "답변 내용", required = true, dataType = "string", paramType = "body")
    })
    @PostMapping("/{id}/answer")
    public ResponseEntity<String> answerInquiry(@PathVariable Long id, @RequestBody String answer) {
        inquiryService.saveAnswer(id, answer);
        return ResponseEntity.ok("답변이 등록되었습니다.");
    }

    /**
     * 특정 문의를 삭제합니다.
     * @param id 문의 ID
     * @return 삭제 완료 메시지
     */
    @ApiOperation(value = "문의 삭제", notes = "문의 ID를 기준으로 해당 문의를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "문의 ID", required = true, dataType = "long", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.ok("문의가 삭제되었습니다.");
    }
}