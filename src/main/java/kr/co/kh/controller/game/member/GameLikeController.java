package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameLikeVO;
import kr.co.kh.service.GameLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/game/member") // 이 컨트롤러의 공통 URL Prefix
@Slf4j // 로그 사용을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class GameLikeController {

    private final GameLikeService gameLikeService;

    /**
     * 찜 등록 또는 취소
     * @param vo 게임ID와 유저이름이 포함된 요청 객체
     * @return "찜 등록이 완료되었습니다." 또는 "찜이 취소되었습니다."
     */
    @ApiOperation(
            value = "찜 등록/취소 처리",
            notes = "게임 ID와 유저 네임이 일치하는 정보가 있으면 삭제, 없으면 찜을 등록합니다.\n\n" +
                    "성공 시: 찜 등록 또는 취소 결과 메시지 반환"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vo", value = "찜 요청 정보(GameLikeVO)", required = true, dataType = "GameLikeVO", paramType = "body")
    })
    @PostMapping("/like/save")
    public ResponseEntity<?> likeSave(@RequestBody GameLikeVO vo) {
        String result = gameLikeService.toggleGameLike(vo);
        return ResponseEntity.ok(result);
    }

    /**
     * 게임 좋아요 여부 체크
     * @param gameId 게임 ID
     * @param user 현재 로그인한 사용자 정보
     * @return true or false
     */
    @ApiOperation(
            value = "게임 좋아요 여부 확인",
            notes = "게임 상세 페이지 진입 시, 현재 사용자가 해당 게임을 찜했는지 여부를 반환합니다.\n\n" +
                    "true: 찜한 상태, false: 찜하지 않은 상태"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/like/checkLike/{gameId}")
    public ResponseEntity<Boolean> checkLike(
            @PathVariable Long gameId,
            @CurrentUser CustomUserDetails user
    ) {
        boolean result = gameLikeService.checkLike(gameId, user);
        return ResponseEntity.ok(result);
    }

    /**
     * 유저의 찜 목록 조회
     * @param userName 유저 이름
     * @return 해당 유저가 찜한 게임 목록
     */
    @ApiOperation(
            value = "유저 찜 목록 조회",
            notes = "특정 유저의 찜 게임 목록을 반환합니다. (cartType이 'LIKE'인 항목만 필터링)"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "유저 이름", required = true, dataType = "string", paramType = "path")
    })
    @GetMapping("/like/list/{userName}")
    public ResponseEntity<?> getWishlistByUser(@PathVariable String userName) {
        try {
            List<GameLikeVO> res = gameLikeService.getWishlistByUser(userName);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("찜 목록 조회 실패", e);
            return ResponseEntity.internalServerError().body("찜 목록 조회 실패");
        }
    }
}