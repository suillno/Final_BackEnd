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

    // ✅ 생성자 주입 (생략된 생성자 자동 생성)
    private final GameApiService gameApiService;

    /**
     * 게임 목록 조회 API
     */
    @GetMapping("/list")
    public ResponseEntity<String> getGames(@RequestParam int page) {
        String result = gameApiService.getGameList(page);
        return ResponseEntity.ok(result);
    }

    /**
     * 게임 상세 조회 API
     */
    @GetMapping("/detail/{gameId}")
    public ResponseEntity<String> getGameDetail(@PathVariable String gameId) {
        String result = gameApiService.getGameDetail(gameId);
        return ResponseEntity.ok(result);
    }

    // [Steam AppId 검색] - 게임 이름을 통해 AppID 추출
    @GetMapping("/search")
    public ResponseEntity<String> searchSteamGame(@RequestParam("q") String gameName) {
        String result = gameApiService.searchSteamGame(gameName);
        return ResponseEntity.ok(result);
    }

    // [Steam 가격 조회] - AppID를 기반으로 가격 정보 가져오기
    @GetMapping("/price/{appId}")
    public ResponseEntity<String> getSteamGamePrice(@PathVariable String appId) {
        String result = gameApiService.getSteamGamePrice(appId);
        return ResponseEntity.ok(result);
    }
}
