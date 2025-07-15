package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.DashBoardVO;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;
import kr.co.kh.service.GameCartService;
import kr.co.kh.service.GameMemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/game/member") // 이 컨트롤러의 공통 URL Prefix
@Slf4j // 로그 사용을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class GameCartController {

    // 장바구니 저장 로직을 처리하는 서비스
    private final GameCartService gameCartService;
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
        String result = "";
        // 1. 구매 요청인 경우
        if (vo.isPurchase()) {
            // 라이브러리에 저장
            result = gameCartService.saveGameLibrary(vo);
            // 장바구니에서 삭제
            gameCartService.toggleGameCart(vo);
            return ResponseEntity.ok(result);
        }
        // 2. 장바구니 추가/삭제 토글
        result = gameCartService.toggleGameCart(vo);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "찜 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/cart/checkCart/{gameId}")
    public ResponseEntity<Boolean> checkCart(@PathVariable Long gameId, @CurrentUser CustomUserDetails user) {
        boolean result = gameCartService.checkCart(gameId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "유저 장바구니 조회 (경로 통일)",
            notes = "유저 장바구니 조회 유저 네임으로"
    )
    @GetMapping("/cart/list/{userName}")
    public ResponseEntity<?> getCartListByUser(@PathVariable String userName) {
        try {
            return ResponseEntity.ok(gameCartService.getCartByUser(userName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("장바구니 조회 실패");
        }

    }


    @GetMapping("/library/all/{userName}")
    public ResponseEntity<?> getAllLibraryByUser(@PathVariable String userName) {
        List<GameLibraryVO> result = gameCartService.getAllLibraryByUser(userName);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 유저의 라이브러리에 게임이 존재하지 않습니다.");
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/dashboard/{userName}")
    public ResponseEntity<List<DashBoardVO>> getDashboard(@PathVariable String userName) {
        List<DashBoardVO> list = gameCartService.getUserByDashBoard(userName);
        return ResponseEntity.ok(list);
    }


}
