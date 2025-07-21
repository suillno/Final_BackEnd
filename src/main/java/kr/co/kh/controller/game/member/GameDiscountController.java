package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.GameDiscountVO;
import kr.co.kh.model.vo.GameGroupVO;
import kr.co.kh.service.GameDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/game/member") // 이 컨트롤러의 공통 URL Prefix
@Slf4j // 로그 사용을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class GameDiscountController {

    private final GameDiscountService gameDiscountService;

    @ApiOperation(
            value = "할인 게임 조회",
            notes = "할인 게임 조회 ResponseEntity 리턴"
    )
    @GetMapping("/discount/list/{page}")
    public ResponseEntity<?> getDiscountList(@PathVariable Long page) {
        try {
            return ResponseEntity.ok(gameDiscountService.getDiscountList(page));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("할인게임 조회 실패");
        }
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "할인 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/discount/checkDiscount/{gameId}")
    public ResponseEntity<Boolean> checkDiscount(@PathVariable Long gameId) {
        boolean result = gameDiscountService.checkDiscount(gameId);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "할인가 적용 저장",
            notes = "관리자가 입력한 할인가격을 저장합니다."
    )
    @PostMapping("/discount/apply")
    public ResponseEntity<?> discountApply(
            @RequestBody GameDiscountVO vo
    ) {
        try {
            // 서비스 계층 호출
            String result = gameDiscountService.toggleDiscount(vo);
            return ResponseEntity.ok(result); // ex) "SUCCESS: 할인가 적용 완료"
        } catch (Exception e) {
            log.error("할인가 저장 오류", e);
            return ResponseEntity.internalServerError().body("할인가 저장 실패");
        }
    }

    @ApiOperation(
            value = "공동구매 예약",
            notes = "공동구매 예약 버튼클릭시 동작"
    )
    @PostMapping("/discount/reservation")
    public ResponseEntity<?> GroupReservation(@RequestBody GameGroupVO vo) {
        try {
            gameDiscountService.groupReservation(vo);
            return ResponseEntity.ok(vo.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("공동구매 예약 실패");
        }
    }
}
