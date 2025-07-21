package kr.co.kh.controller.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${steam-api.search-url}")
    private String steamSearchUrl;

    @Value("${steam-api.price-url}")
    private String steamPriceUrl;

    /**
     * 게임 리스트를 조회합니다.
     * @param page 페이지 번호
     * @return 게임 리스트 JSON 문자열
     */
    @ApiOperation(value = "게임 리스트 조회", notes = "페이지 번호를 기준으로 게임 목록을 조회합니다.")
    @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, dataType = "int", paramType = "query")
    @GetMapping("/list")
    public ResponseEntity<String> getGames(@RequestParam(defaultValue = "1") int page) {
        String result = gameApiService.getGameList(page);
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 연도에 출시된 게임 목록을 조회합니다.
     * @param gameYearList 연도 문자열 (예: "2023")
     * @param page 페이지 번호
     * @return 해당 연도 게임 리스트 JSON 문자열
     */
    @ApiOperation(value = "출시 연도별 게임 조회", notes = "특정 연도에 출시된 게임 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameYearList", value = "게임 출시 연도", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("/gameYearList")
    public ResponseEntity<String> getGameYear(@RequestParam String gameYearList, @RequestParam(defaultValue = "1") int page) {
        String result = gameApiService.getGameYear(gameYearList, page);
        return ResponseEntity.ok(result);
    }

    /**
     * 플레이 시간이 긴 게임을 조회합니다.
     * @param gamePlayTime 플레이 시간 조건
     * @param page 페이지 번호
     * @return 장시간 플레이 게임 리스트 JSON
     */
    @ApiOperation(value = "플레이타임 순 게임 조회", notes = "가장 오래 플레이된 게임 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gamePlayTime", value = "플레이타임 기준", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("/gameLongPlayList")
    public ResponseEntity<String> getGameTime(@RequestParam String gamePlayTime, @RequestParam(defaultValue = "1") int page) {
        String result = gameApiService.getGameTime(gamePlayTime, page);
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 게임의 상세 정보를 조회합니다.
     * @param gameId 게임 ID
     * @return 게임 상세 정보 JSON
     */
    @ApiOperation(value = "게임 상세 조회", notes = "게임 ID를 기준으로 상세 정보를 조회합니다.")
    @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "string", paramType = "path")
    @GetMapping("/detail/{gameId}")
    public ResponseEntity<String> getGameDetail(@PathVariable String gameId) {
        String result = gameApiService.getGameDetail(gameId);
        return ResponseEntity.ok(result);
    }

    /**
     * 특정 게임의 이미지를 조회합니다.
     * @param gameId 게임 ID
     * @return 이미지 URL JSON
     */
    @ApiOperation(value = "게임 이미지 조회", notes = "게임 ID를 통해 이미지를 조회합니다.")
    @ApiImplicitParam(name = "gameId", value = "게임 ID", required = true, dataType = "string", paramType = "query")
    @GetMapping("/img")
    public ResponseEntity<String> getGameImg(@RequestParam String gameId) {
        String result = gameApiService.getGameImg(gameId);
        return ResponseEntity.ok(result);
    }

    /**
     * 장르별 게임 목록을 조회합니다.
     * @param genres 장르 이름 또는 ID
     * @param page 페이지 번호
     * @return 장르 기반 게임 리스트 JSON
     */
    @ApiOperation(value = "장르별 게임 조회", notes = "지정한 장르에 해당하는 게임 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "genres", value = "게임 장르", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("/genres")
    public ResponseEntity<String> getGameGenres(@RequestParam(defaultValue = "1") String genres, @RequestParam(defaultValue = "1") int page) {
        String result = gameApiService.getGameGenres(genres, page);
        return ResponseEntity.ok(result);
    }

    /**
     * 게임 타이틀로 RAWG API에서 검색합니다.
     * @param gameTitle 검색할 게임 이름
     * @return 검색 결과 JSON
     */
    @ApiOperation(value = "게임 이름 검색", notes = "게임 타이틀을 기준으로 RAWG API에서 게임을 검색합니다.")
    @ApiImplicitParam(name = "search", value = "게임 이름", required = true, dataType = "string", paramType = "query")
    @GetMapping("/search/rwag")
    public ResponseEntity<String> getSearchGame(@RequestParam(name = "search") String gameTitle) {
        String result = gameApiService.getSearchGame(gameTitle);
        return ResponseEntity.ok(result);
    }

    /**
     * 플랫폼별 게임 목록을 조회합니다.
     * @param platformId 플랫폼 ID
     * @param page 페이지 번호
     * @return 해당 플랫폼의 게임 목록 JSON
     */
    @ApiOperation(value = "플랫폼별 게임 조회", notes = "지정한 플랫폼 ID로 게임 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platforms", value = "플랫폼 ID", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "페이지 번호", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("/gamePlatform")
    public ResponseEntity<String> getGamesPlatform(@RequestParam(name = "platforms", defaultValue = "1") String platformId, @RequestParam(defaultValue = "1") int page) {
        log.info("플랫폼별 게임 호출 도달");
        String result = gameApiService.getGamesPlatform(platformId, page);
        return ResponseEntity.ok(result);
    }

    /**
     * 스팀에서 게임 이름으로 검색하여 appId를 조회합니다.
     * @param query 검색어
     * @return 검색 결과 JSON
     */
    @ApiOperation(value = "스팀 게임 검색", notes = "스팀 API를 통해 게임 이름으로 검색하여 appId를 조회합니다.")
    @ApiImplicitParam(name = "q", value = "검색어", required = true, dataType = "string", paramType = "query")
    @GetMapping("/search")
    public ResponseEntity<?> searchApp(@RequestParam(name = "q", defaultValue = "") String query) {
        String url = steamSearchUrl + query;
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * 스팀에서 게임 가격을 조회합니다.
     * @param appId 스팀 app ID
     * @return 가격 정보 JSON
     */
    @ApiOperation(value = "스팀 가격 조회", notes = "스팀 appId를 기반으로 가격 정보를 조회합니다.")
    @ApiImplicitParam(name = "appId", value = "스팀 App ID", required = true, dataType = "string", paramType = "path")
    @GetMapping("/price/{appId}")
    public ResponseEntity<?> getPrice(@PathVariable String appId) {
        String url = steamPriceUrl + appId;
        return restTemplate.getForEntity(url, String.class);
    }
}