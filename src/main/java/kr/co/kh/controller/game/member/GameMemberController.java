package kr.co.kh.controller.game.member;

// Swagger API 문서 작성을 위한 어노테이션
import io.swagger.annotations.*;

import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.vo.GameCartVO; // 장바구니에 담길 게임 정보 VO
import kr.co.kh.model.vo.GameLikeVO;
import kr.co.kh.model.vo.GameReviewVO;

// 롬복 어노테이션
import kr.co.kh.service.GameMemberService;
import kr.co.kh.service.MailService;
import kr.co.kh.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Spring MVC 관련 어노테이션
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        // 장바구니에 게임 정보 저장 처리
        String result = gameMemberService.toggleGameCart(vo);
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

    /**
     * 리뷰 등록 API
     * @param vo 클라이언트로부터 전달받은 리뷰 정보
     * @return 저장 성공 메시지를 포함한 HTTP 응답
     */
    @ApiOperation(
            value = "리뷰 등록",
            notes = "게임 리뷰를 저장합니다."
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
    @GetMapping("/review/checkLike/{gameId}")
    public ResponseEntity<Boolean> checkLike(@PathVariable Long gameId, @CurrentUser CustomUserDetails user) {
        boolean result = gameMemberService.checkLike(gameId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "찜 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/review/checkCart/{gameId}")
    public ResponseEntity<Boolean> checkCart(@PathVariable Long gameId, @CurrentUser CustomUserDetails user) {
        boolean result = gameMemberService.checkCart(gameId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "유저 장바구니 조회 (경로 통일)",
            notes = "/game/member/cart/list/{username} 경로로 모든 cart API 통일"
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




}
