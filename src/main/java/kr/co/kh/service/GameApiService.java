package kr.co.kh.service;

import java.util.Map;

/**
 * Game API 서비스 인터페이스
 * 외부 RAWG API와의 통신을 담당하는 서비스 인터페이스입니다.
 * 게임 목록 조회와 게임 상세 조회 기능을 정의합니다.
 */
public interface GameApiService {

    /**
     * 게임 목록을 조회합니다.
     * @param page 조회할 페이지 번호 (페이징 처리)
     * @return RAWG API에서 반환된 JSON 문자열 (게임 목록 데이터)
     */
    String getGameList(int page);

    /**
     * 2024 인기게임 조회
     * @param gameYearList
     * @param page
     * @return
     */
    String getGameYear( String gameYearList, int page);

    /**
     * 가장 오래한 플레이타임 조회
     * @param gamePlayTime
     * @param page
     * @return
     */
    String getGameTime( String gamePlayTime, int page);

    /**
     * 장르별 검색 기능
     * @param genres
     * @param page
     * @return
     */
    String getGameGenres( String genres, int page);

    /**
     * 특정 게임의 상세 정보를 조회합니다.
     *
     * @param gameId 조회할 게임의 ID
     * @return RAWG API에서 반환된 JSON 문자열 (게임 상세 데이터)
     */
    String getGameDetail(String gameId);

    String getSearchGame(String gameTitle);

    String getGameImg(String gameId);

    /**
     * 게임 플렛폼 api 목록 가져오기
     * @param platformId
     * @param page
     * @return
     */

    String getGamesPlatform(String platformId, int page);
}
