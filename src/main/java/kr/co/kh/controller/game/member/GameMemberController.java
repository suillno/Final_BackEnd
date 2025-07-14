package kr.co.kh.controller.game.member;

// Swagger API 문서 작성을 위한 어노테이션
import io.swagger.annotations.*;

import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.vo.*;

// 롬복 어노테이션
import kr.co.kh.service.GameCartService;
import kr.co.kh.service.GameMemberService;
import kr.co.kh.service.MailService;
import kr.co.kh.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Spring MVC 관련 어노테이션
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게임 회원 기능을 처리하는 컨트롤러 클래스
 * 장바구니 등록 등 회원과 관련된 게임 처리 엔드포인트 정의
 */
@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/game/member") // 이 컨트롤러의 공통 URL Prefix
@Slf4j // 로그 사용을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class GameMemberController {

    // 장바구니 저장 로직을 처리하는 서비스
    private final GameMemberService gameMemberService;
    private final GameCartService gameCartService;

    /**
     * 장바구니 저장 API
     * @param vo 클라이언트로부터 전달받은 게임 장바구니 정보
     * @return 저장 성공 메시지를 포함한 HTTP 200 응답
     */
    @ApiOperation(
            value = "장바구니 등록",
            notes = "게임 정보를 장바구니에 저장합니다."
    )
    @PostMapping("/cart/save")
    public ResponseEntity<?> cartSave(
            @RequestBody GameCartVO vo // 요청 본문에서 JSON → GameCartVO 변환
    ) {
        String result = "";

        // 1. 구매 요청인 경우
        if (vo.isPurchase()) {
            // 라이브러리에 저장
            result = gameCartService.saveGameLibrary(vo);

            // 장바구니에서 삭제
            gameMemberService.toggleGameCart(vo);

            return ResponseEntity.ok(result);
        }

        // 2. 장바구니 추가/삭제 토글
        result = gameMemberService.toggleGameCart(vo);
        return ResponseEntity.ok(result);

    }

    @ApiOperation(
            value = "프로시저 사용 찜 등록",
            notes = "(게임 아이디, 유저 네임)과 일치하는 정보가있으면 삭제, " +
                    "일치하는 정보가 없으면 저장."
    )
    @PostMapping("/like/save")
    public ResponseEntity<?> likeSave(
            @RequestBody GameLikeVO vo
    ) {
        String result = gameMemberService.toggleGameLike(vo);
        return ResponseEntity.ok(result); // "찜 등록이 완료되었습니다." 또는 "찜이 취소되었습니다."
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
            String result = gameMemberService.toggleDiscount(vo);
            return ResponseEntity.ok(result); // ex) "SUCCESS: 할인가 적용 완료"
        } catch (Exception e) {
            log.error("할인가 저장 오류", e);
            return ResponseEntity.internalServerError().body("할인가 저장 실패");
        }
    }


    /**
     * 리뷰 등록 API
     * @param vo 클라이언트로부터 전달받은 리뷰 정보
     * @return 저장 성공 메시지를 포함한 HTTP 응답
     */
    @ApiOperation(
            value = "리뷰 삭제",
            notes = "게임 리뷰를 삭제합니다."
    )
    @PostMapping("/review/add")
    public ResponseEntity<?> reviewSave(
            @RequestBody GameReviewVO vo
    ) {
        boolean result = gameMemberService.reviewSave(vo);
        if (result) {
            return ResponseEntity.ok("리뷰를 등록하였습니다.");
        } else {
            return ResponseEntity.ok("리뷰를 수정하였습니다.");
        }
    }

    @DeleteMapping("/review")
    @ApiOperation(value = "리뷰 삭제", notes = "게임 리뷰를 삭제합니다.")
    public ResponseEntity<?> reviewDelete(@RequestParam String userName, @RequestParam Long gameId) {
        int result = gameMemberService.reviewDelete(userName, gameId); // userName 전달
            return ResponseEntity.ok("리뷰를 삭제하였습니다.");
    }

    /**
     * 게임별 리뷰 목록 조회 API
     * @param gameId 리뷰를 조회할 게임 ID
     * @return 해당 게임의 리뷰 리스트
     */
    @ApiOperation(
            value = "리뷰 목록 조회",
            notes = "특정 게임에 대한 리뷰 목록을 반환합니다."
    )
    @GetMapping("/review/list/{gameId}")
    public ResponseEntity<?> getReviewList(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.ok(gameMemberService.reviewListByGameId(gameId));
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "좋아요 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/like/checkLike/{gameId}")
    public ResponseEntity<Boolean> checkLike(@PathVariable Long gameId, @CurrentUser CustomUserDetails user) {
        boolean result = gameMemberService.checkLike(gameId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "찜 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/cart/checkCart/{gameId}")
    public ResponseEntity<Boolean> checkCart(@PathVariable Long gameId, @CurrentUser CustomUserDetails user) {
        boolean result = gameMemberService.checkCart(gameId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "할인 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/discount/checkDiscount/{gameId}")
    public ResponseEntity<Boolean> checkDiscount(@PathVariable Long gameId) {
        boolean result = gameMemberService.checkDiscount(gameId);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "게임 상태 일괄 확인",
            notes = "좋아요, 장바구니, 할인 여부를 Map으로 반환")
    @GetMapping("/status/checkAll/{gameId}")
    public ResponseEntity<Map<String, Boolean>> checkStatus(
            @PathVariable Long gameId,
            @CurrentUser CustomUserDetails user) {

        Map<String, Boolean> statusMap = new HashMap<>();
        statusMap.put("like", gameMemberService.checkLike(gameId, user));
        statusMap.put("cart", gameMemberService.checkCart(gameId, user));
        statusMap.put("discount", gameMemberService.checkDiscount(gameId));

        return ResponseEntity.ok(statusMap);
    }

    @ApiOperation(
            value = "유저 장바구니 조회 (경로 통일)",
            notes = "유저 장바구니 조회 유저 네임으로"
    )
    @GetMapping("/cart/list/{userName}")
    public ResponseEntity<?> getCartListByUser(@PathVariable String userName) {
        try {
            return ResponseEntity.ok(gameMemberService.getCartByUser(userName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("장바구니 조회 실패");
        }
    }

    @ApiOperation(
            value = "유저 찜 목록 조회",
            notes = "cartType이 'LIKE'인 항목만 필터링하여 반환합니다."
    )
    @GetMapping("/like/list/{userName}")
    public ResponseEntity<?> getWishlistByUser(@PathVariable String userName) {
        try {
            return ResponseEntity.ok(gameMemberService.getWishlistByUser(userName)); // 서비스에서 분기처리
        } catch (Exception e) {
            log.error("찜 목록 조회 실패", e);
            return ResponseEntity.internalServerError().body("찜 목록 조회 실패");
        }
    }

    @ApiOperation(
            value = "할인 게임 조회",
            notes = "할인 게임 조회 ResponseEntity 리턴"
    )
    @GetMapping("/discount/list/{page}")
    public ResponseEntity<?> getDiscountList(@PathVariable Long page) {
        try {
            return ResponseEntity.ok(gameMemberService.getDiscountList(page));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("할인게임 조회 실패");
        }
    }

    @ApiOperation(
            value = "공동구매 예약",
            notes = "공동구매 예약 버튼클릭시 동작"
    )
    @PostMapping("/discount/reservation")
    public ResponseEntity<?> GroupReservation(@RequestBody GameGroupVO vo) {
        try {
            gameMemberService.groupReservation(vo);
            return ResponseEntity.ok(vo.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("공동구매 예약 실패");
        }
    }



    /**
     *  방문자 기록 API
     *
     *  기능 설명:
     * - 사용자가 메인 페이지에 접근할 때 호출되어,
     *   해당 사용자의 방문 기록을 `VISITOR_LOG` 테이블에 저장합니다.
     * - 단, 같은 날짜에 이미 방문 기록이 있는 경우 중복 저장되지 않음 (MERGE INTO 쿼리로 처리).
     * - 이 기록은 차트 통계(예: 일별 방문자 수, 누적 방문자 수 등)를 위한 데이터로 활용됩니다.
     *
     *  전제 조건:
     * - 사용자가 로그인되어 있어야 하며, 토큰 기반 인증을 통해 `@CurrentUser`로 사용자 정보를 주입받습니다.
     *
     *  요청 방식: POST
     *  요청 경로: /game/member/log/visit
     *  응답: 성공 시 "방문 기록 완료", 실패 시 500 에러 메시지 반환
     */
    @PostMapping("/log/visit")
    public ResponseEntity<?> logVisitor(@CurrentUser CustomUserDetails user) {
        try {
            // 현재 로그인한 사용자의 ID를 가져옴 (User 클래스를 상속받았기 때문에 바로 getId() 사용 가능)
            Long userId = user.getId();

            // 방문자 기록 INSERT (중복 방지를 위해 같은 날짜엔 한 번만 기록됨)
            gameMemberService.insertVisitorLog(userId);

            // 클라이언트에게 방문 기록 성공 메시지 반환
            return ResponseEntity.ok("방문 기록 완료");
        } catch (Exception e) {
            // 예외 발생 시 에러 로그 출력 및 500 응답
            log.error("방문자 기록 실패", e);
            return ResponseEntity.internalServerError().body("방문 기록 실패");
        }
    }






}
