package kr.co.kh.controller.game.member;

import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameLikeVO;
import kr.co.kh.service.GameLikeService;
import kr.co.kh.service.GameMemberService;
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

    @ApiOperation(
            value = "프로시저 사용 찜 등록",
            notes = "(게임 아이디, 유저 네임)과 일치하는 정보가있으면 삭제, " +
                    "일치하는 정보가 없으면 저장."
    )
    @PostMapping("/like/save")
    public ResponseEntity<?> likeSave(
            @RequestBody GameLikeVO vo
    ) {
        log.info("tests : {}",vo.toString());
        String result = gameLikeService.toggleGameLike(vo);
        return ResponseEntity.ok(result); // "찜 등록이 완료되었습니다." 또는 "찜이 취소되었습니다."
    }

    @ApiOperation(
            value = "게임 상세 페이지 진입 시 좋아요 여부 체크",
            notes = "좋아요 클릭시 데이터를 반환해서 좋아요 색상 표시")
    @GetMapping("/like/checkLike/{gameId}")
    public ResponseEntity<Boolean> checkLike(@PathVariable Long gameId, @CurrentUser CustomUserDetails user) {
        boolean result = gameLikeService.checkLike(gameId, user);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(
            value = "유저 찜 목록 조회",
            notes = "cartType이 'LIKE'인 항목만 필터링하여 반환합니다."
    )
    @GetMapping("/like/list/{userName}")
    public ResponseEntity<?> getWishlistByUser(@PathVariable String userName) {
        try {
            List<GameLikeVO> res = gameLikeService.getWishlistByUser(userName);
            return ResponseEntity.ok(res); // 서비스에서 분기처리
        } catch (Exception e) {
            log.error("찜 목록 조회 실패", e);
            return ResponseEntity.internalServerError().body("찜 목록 조회 실패");
        }
    }
}
