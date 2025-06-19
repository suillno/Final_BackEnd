package kr.co.kh.controller.api;

import kr.co.kh.service.GameApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    /**
     * 게임 타이틀로 조회
     * @param gameTitle
     * @return
     */
    @GetMapping("/search/rwag")
    public ResponseEntity<String> getSearchGame(@RequestParam("search") String gameTitle) {
        String result = gameApiService.getSearchGame(gameTitle);
        return ResponseEntity.ok(result);
    }


    // 스팀조회
    private final RestTemplate restTemplate = new RestTemplate();

    // 스팀 API search URL
    @Value("${steam-api.search-url}")
    private String steamSearchUrl;

    // 스팀 API price URL
    @Value("${steam-api.price-url}")
    private String steamPriceUrl;

    /**
     * 스팀 API 타이틀로 검색 => API ID 값 찿기
     * @param query
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchApp(@RequestParam("q") String query) {
        String url = steamSearchUrl + query;
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * 스팀 API ID로 가격 조회하기
     * @param appId
     * @return
     */
    @GetMapping("/price/{appId}")
    public ResponseEntity<?> getPrice(@PathVariable("appId") String appId) {
        String url = steamPriceUrl + appId;
        return restTemplate.getForEntity(url, String.class);
    }
}


