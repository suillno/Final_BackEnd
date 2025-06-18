package kr.co.kh.service.impl;

import kr.co.kh.service.GameApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * GameApiService 구현체
 * <p>
 * RAWG 외부 API와 통신하여 게임 데이터를 조회하는 서비스 클래스입니다.
 * 외부 API 호출은 RestTemplate을 사용하여 수행됩니다.
 */
@Service
public class GameApiServiceImpl implements GameApiService {


     // RAWG API의 기본 URL (application.yml에서 주입받음)
    @Value("${game-api.url}")
    private String apiUrl;


     // RAWG API 키 (application.yml에서 주입받음)
    @Value("${game-api.key}")
    private String apiKey;





    /**
     * REST 통신을 위한 RestTemplate 객체
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * RAWG API를 호출하여 게임 목록을 조회합니다.
     *
     * @param page 조회할 페이지 번호
     * @return RAWG API로부터 받은 JSON 문자열 (게임 목록)
     */
    @Override
    public String getGameList(int page) {
        String url = apiUrl + "/games?key=" + apiKey + "&page=" + page + "&page_size=20";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    /**
     * RAWG API를 호출하여 특정 게임의 상세 정보를 조회합니다.
     *
     * @param gameId 조회할 게임의 ID
     * @return RAWG API로부터 받은 JSON 문자열 (게임 상세 정보)
     */
    @Override
    public String getGameDetail(String gameId) {
        String url = apiUrl + "/games/" + gameId + "?key=" + apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
