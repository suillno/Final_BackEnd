package kr.co.kh.controller.game.member;

import io.swagger.annotations.*; // Swagger 어노테이션
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameReviewVO;
import kr.co.kh.service.game.service.GameMemberService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * 게임 회원 기능을 처리하는 컨트롤러 클래스
 * 장바구니, 리뷰 등록 등 API를 담당
 */
@RestController
@RequestMapping("/game/member")
@Slf4j
@AllArgsConstructor
@Api(tags = "게임 회원 기능", description = "장바구니 및 리뷰 저장 API")
public class GameMemberController {

    private final GameMemberService gameMemberService;

    /**
     * 장바구니 저장 API
     * @param vo 게임 장바구니 정보
     * @return 성공 메시지
     */
    @ApiOperation(value = "장바구니 등록", notes = "게임 정보를 장바구니에 저장합니다.")
    @ApiImplicitParam(
            name = "GameCartVO",
            value = "장바구니 저장에 필요한 게임 정보",
            required = true,
            dataType = "GameCartVO",
            paramType = "body"
    )
    @PostMapping("/cartsave")
    public ResponseEntity<?> cartSave(@RequestBody GameCartVO vo) {
        gameMemberService.cartSave(vo);
        return ResponseEntity.ok("장바구니 담기 완료");
    }

    /**
     * 게임 리뷰 등록 API
     * @param vo 게임 리뷰 정보
     * @return 성공 메시지
     */
    @ApiOperation(value = "리뷰 등록", notes = "게임에 대한 리뷰를 저장합니다.")
    @ApiImplicitParam(
            name = "GameReviewVO",
            value = "리뷰 작성에 필요한 정보",
            required = true,
            dataType = "GameReviewVO",
            paramType = "body"
    )
    @PostMapping("/reviewsave")
    public ResponseEntity<?> reviewSave(@RequestBody GameReviewVO vo) {
        gameMemberService.reviewSave(vo);
        return ResponseEntity.ok("리뷰 저장 완료");
    }

    @GetMapping("/review")
    public ResponseEntity<?> reviewList(@RequestParam("gameId") Long gameId) {
        return ResponseEntity.ok(gameMemberService.reviewListByGameId(gameId));
    }

}
