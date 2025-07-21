package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    /**
     * 할인 게임 조회
     * @param page 페이지 번호
     * @return 할인 게임 리스트 (페이징 포함)
     */
    @ApiOperation(
            value = "할인 게임 조회",
            notes = "할인 중인 게임 리스트를 페이징 처리하여 반환합니다.\n\n" +
                    "성공 시: 게임 목록과 페이징 정보를 포함한 Map<String, Object> 반환\n" +
                    "실패 시: 오류 메시지 문자열"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/discount/list/{page}")
    public ResponseEntity<?> getDiscountList(@PathVariable Long page) {
        try {
            return ResponseEntity.ok(gameDiscountService.getDiscountList(page));
        } catch (Exception e) {
            log.error("할인게임 조회 실패", e);
            return ResponseEntity.internalServerError().body("할인게임 조회 실패");
        }
    }

    /**
     * 게임 할인 여부 확인
     * @param gameId 게임 ID
     * @return true/false
     */
    @ApiOperation(
            value = "게임 할인 여부 확인",
            notes = "해당 게임이 현재 할인 중인지 확인하여 true 또는 false 반환합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/discount/checkDiscount/{gameId}")
    public ResponseEntity<Boolean> checkDiscount(@PathVariable Long gameId) {
        boolean result = gameDiscountService.checkDiscount(gameId);
        return ResponseEntity.ok(result);
    }

    /**
     * 할인가 적용 저장
     * @param vo GameDiscountVO
     * @return 처리 결과 메시지
     */
    @ApiOperation(
            value = "할인가 적용 저장",
            notes = "관리자가 입력한 할인가격을 저장합니다.\n\n" +
                    "성공 시: 'SUCCESS: 할인가 적용 완료'"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo", value = "게임 디스카운트 VO", required = true, dataType = "GameDiscountVO", paramType = "body")
    })
    @PostMapping("/discount/apply")
    public ResponseEntity<?> discountApply(@RequestBody GameDiscountVO vo) {
        try {
            String result = gameDiscountService.toggleDiscount(vo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("할인가 저장 오류", e);
            return ResponseEntity.internalServerError().body("할인가 저장 실패");
        }
    }

    /**
     * 공동구매 예약
     * @param vo GameGroupVO
     * @return 처리 결과 메시지
     */
    @ApiOperation(
            value = "공동구매 예약",
            notes = "공동구매 예약 버튼 클릭 시 예약을 진행합니다.\n\n" +
                    "성공 시: 'SUCCESS: 예약 완료'"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo", value = "공동구매 정보 VO", required = true, dataType = "GameGroupVO", paramType = "body")
    })
    @PostMapping("/discount/reservation")
    public ResponseEntity<?> groupReservation(@RequestBody GameGroupVO vo) {
        try {
            gameDiscountService.groupReservation(vo);
            return ResponseEntity.ok(vo.getResult());
        } catch (Exception e) {
            log.error("공동구매 예약 실패", e);
            return ResponseEntity.internalServerError().body("공동구매 예약 실패");
        }
    }
}
