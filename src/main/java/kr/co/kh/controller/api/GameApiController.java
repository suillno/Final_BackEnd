package kr.co.kh.controller.api;

import kr.co.kh.service.GameApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
public class GameApiController {

    // 생성자 주입 (생략된 생성자 자동 생성)
    private final GameApiService gameApiService;

    /**
     * 게임 목록 조회 API
     */
    @GetMapping("/list")
    public ResponseEntity<String> getGames(@RequestParam(defaultValue = "1") int page) {
        String result = gameApiService.getGameList(page);
        return ResponseEntity.ok(result);
    }

    /**
     * @param gameYearList
     * @param page
     * @return
     */
    @GetMapping("/gameYearList")
    public  ResponseEntity<String> getGameYear(
            @RequestParam(name = "gameYearList") String gameYearList,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) { String result = gameApiService.getGameYear(gameYearList, page);
        return ResponseEntity.ok(result);
    }

    /**
     * 가장 오래플레이한 게임 조회
     * @param gamePlayTime
     * @param page
     * @return
     */
    @GetMapping("/gameLongPlayList")
    public ResponseEntity<String> getGameTime(
            @RequestParam(name = "gamePlayTime") String gamePlayTime,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {String result = gameApiService.getGameTime(gamePlayTime, page);
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
     * 게임 이미지 가져오기
     * @param gameId
     * @return
     */
    @GetMapping("/img")
    public ResponseEntity<String> getGameImg(@RequestParam String gameId) {
        String result = gameApiService.getGameImg(gameId);
        return ResponseEntity.ok(result);
    }

    /**
     * 장르별 게임목록 조회
     * @param genres
     * @param page
     * @return
     */
    @GetMapping("/genres")
    public ResponseEntity<String> getGameGenres(
            @RequestParam(name = "genres", defaultValue = "1") String genres,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        String result = gameApiService.getGameGenres(genres, page);
        return ResponseEntity.ok(result);
    }


    /**
     * 게임 타이틀로 조회
     * @param gameTitle
     * @return
     */
    @GetMapping("/search/rwag")
    public ResponseEntity<String> getSearchGame(@RequestParam(name = "search") String gameTitle) {
        String result = gameApiService.getSearchGame(gameTitle);
        return ResponseEntity.ok(result);
    }

    /**
     * 게임 플렛폼 조회
     */
    @GetMapping("/gamePlatform")
    public ResponseEntity<String> getGamesPlatform(
            @RequestParam(name ="platforms", defaultValue = "1") String platformId,
            @RequestParam(name ="page", defaultValue = "1") int page
    ) {
        log.info("플랫폼별 게임 호출 도달");
        String result = gameApiService.getGamesPlatform(platformId, page);
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
    public ResponseEntity<?> searchApp(@RequestParam(name = "q", defaultValue = "") String query) {
        String url = steamSearchUrl + query;
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * 스팀 API ID로 가격 조회하기
     * @param appId
     * @return
     */
    @GetMapping("/price/{appId}")
    public ResponseEntity<?> getPrice(@PathVariable(name = "appId") String appId) {
        String url = steamPriceUrl + appId;
        return restTemplate.getForEntity(url, String.class);
    }
}


