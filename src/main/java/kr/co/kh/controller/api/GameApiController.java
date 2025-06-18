package kr.co.kh.controller.api;

import kr.co.kh.service.GameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게임 API 컨트롤러
 * <p>
 * 클라이언트로부터의 게임 목록 및 상세 조회 요청을 받아
 * GameApiService를 통해 외부 RAWG API와 통신하고 결과를 반환합니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
public class GameApiController {

    /**
     * RequiredArgsConstructor 활용 생성자 자동생성
     */
    private final GameApiService gameApiService;

    /**
     * 게임 목록 조회 API
     * @param page 조회할 페이지 번호
     * @return 게임 목록 데이터를 포함한 JSON 응답
     */
    @GetMapping("/list")
    public ResponseEntity<String> getGames(@RequestParam int page) {
        String result = gameApiService.getGameList(page);
        return ResponseEntity.ok(result);
    }

    /**
     * 게임 상세 조회 API
     * @param gameId 조회할 게임의 ID
     * @return 게임 상세 데이터를 포함한 JSON 응답
     */
    @GetMapping("/detail/{gameId}")
    public ResponseEntity<String> getGameDetail(@PathVariable String gameId) {
        String result = gameApiService.getGameDetail(gameId);
        return ResponseEntity.ok(result);
    }
}
