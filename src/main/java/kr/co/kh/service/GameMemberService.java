package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameDiscountVO;
import kr.co.kh.model.vo.GameReviewVO;
import java.util.List;
import kr.co.kh.model.vo.GameLikeVO;

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


    // ✅ 유저 장바구니 전체 목록 조회
    List<GameCartVO> getCartByUser(String userName);

    // ✅ 유저 찜(WISH) 목록 조회
    List<GameLikeVO> getWishlistByUser(String userName);

}
