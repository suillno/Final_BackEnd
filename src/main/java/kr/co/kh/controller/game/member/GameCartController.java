package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.DashBoardVO;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;
import kr.co.kh.service.GameCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 장바구니 저장 API
     * @param vo 클라이언트로부터 전달받은 게임 장바구니 정보
     * @return 저장 성공 메시지를 포함한 HTTP 200 응답
     */
    @ApiOperation(
            value = "장바구니 저장 또는 구매 처리",
            notes = "게임 정보를 장바구니에 추가하거나 구매 요청 시 라이브러리에 저장하고 장바구니에서 제거합니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo", value = "장바구니 저장 정보", required = true, dataType = "GameCartVO", paramType = "body")
    })
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

    /**
     * 게임 상세 페이지 진입 시 해당 게임이 장바구니에 있는지 확인
     * @param gameId
     * @param user
     * @return boolean
     */
    @ApiOperation(value = "게임 장바구니 여부 확인", notes = "게임 상세 페이지 진입 시 해당 게임이 장바구니에 있는지 확인합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/cart/checkCart/{gameId}")
    public ResponseEntity<Boolean> checkCart(
            @PathVariable Long gameId,
            @CurrentUser CustomUserDetails user
    ) {
        boolean result = gameCartService.checkCart(gameId, user);
        return ResponseEntity.ok(result);
    }

    /**
     * 유저 장바구니 조회
     * @param userName
     * @return GameCartVO
     */
    @ApiOperation(value = "유저 장바구니 조회", notes = "특정 유저의 장바구니 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "유저 이름", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/cart/list/{userName}")
    public ResponseEntity<?> getCartListByUser(
            @PathVariable String userName
    ) {
        try {
            return ResponseEntity.ok(gameCartService.getCartByUser(userName));
        } catch (Exception e) {
            log.error("예외발생 : {}", e);
            return ResponseEntity.internalServerError().body("장바구니 조회 실패");
        }
    }

    /**
     * 유저가 구매하여 보유하고 있는 게임 전체 목록을 조회
     * @param userName
     * @return GameLibraryVO
     */
    @ApiOperation(value = "유저 보유 게임 목록", notes = "유저가 구매하여 보유하고 있는 게임 전체 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "유저 이름", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/library/all/{userName}")
    public ResponseEntity<List<GameLibraryVO>> getAllLibraryByUser(
            @PathVariable String userName
    ) {
        List<GameLibraryVO> result = gameCartService.getAllLibraryByUser(userName);
        return ResponseEntity.ok(result);
    }

    /**
     * 유저 대시보드 정보 조회
     * @param userName
     * @return DashBoardVO
     */
    @ApiOperation(value = "유저 대시보드 정보", notes = "유저의 게임 구매/보유 관련 대시보드 정보를 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "유저 이름", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/dashboard/{userName}")
    public ResponseEntity<List<DashBoardVO>> getDashboard(
            @PathVariable String userName
    ) {
        List<DashBoardVO> list = gameCartService.getUserByDashBoard(userName);
        return ResponseEntity.ok(list);
    }
}
