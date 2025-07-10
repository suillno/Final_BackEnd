package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.*;

import java.util.List;
import java.util.Map;

public interface GameMemberService {
    // 리뷰 저장
    boolean reviewSave(GameReviewVO vo);
    // 리뷰리스트 조회
    List<GameReviewVO> reviewListByGameId(Long gameId);
    // 프로시저사용 찜
    String toggleGameLike(GameLikeVO vo);
    // 프로시저사용 장바구니
    String toggleGameCart(GameCartVO vo);
    // 프로시저사용 할인가적용
    String toggleDiscount(GameDiscountVO vo);

    // 사용여부 표기 색상변경
    boolean checkLike(Long gameId, CustomUserDetails user);
    boolean checkCart(Long gameId, CustomUserDetails user);
    boolean checkDiscount(Long gameId);


    // 유저 장바구니 전체 목록 조회
    List<GameCartVO> getCartByUser(String userName);
    // 게임할인 조회하기
    Map<String, Object> getDiscountList(Long page);

    // ✅ 유저 찜(WISH) 목록 조회
    List<GameLikeVO> getWishlistByUser(String userName);
    // 공동구매 등록
    void groupReservation(GameGroupVO vo);

    // ✅ 할인 포함 찜 목록 조회 (프로시저 기반)
    List<GameLikeVO> getDiscountWishlist(String userName);

    // 방문자 기록 저장
    void insertVisitorLog(Long userId);

}
