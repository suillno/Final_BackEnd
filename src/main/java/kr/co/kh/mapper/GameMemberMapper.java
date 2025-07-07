package kr.co.kh.mapper;

import kr.co.kh.model.vo.*;
import kr.co.kh.model.vo.GameLikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameMemberMapper {
    // 리뷰 존재 여부 확인 (중복 검사용)
    int countReview(GameReviewVO vo);
    // 리뷰 저장
    int reviewSave(GameReviewVO vo);
    // 리뷰 수정
    int reviewUpdate(GameReviewVO vo);
    // 리뷰 리스트 조회
    List<GameReviewVO> reviewListByGameId(Long gameId);
    // 프로시저 사용 찜
    String toggleGameLike(GameLikeVO vo);
    // 프로시저 사용 장바구니
    String toggleGameCart(GameCartVO vo);
    // 프로시저 사용 할인
    String toggleDiscount(GameDiscountVO vo);
    // 게임 좋아요 기능 데이터 있을시 색상 변경
    int checkLike(GameLikeVO vo);
    int checkCart(GameCartVO vo);
    int checkDiscount(GameDiscountVO vo);

    // ✅ 유저 장바구니 전체 목록 조회
    List<GameCartVO> getCartByUser(String userName);

    // ✅ 유저 찜(WISH) 목록 조회
    List<GameLikeVO> getWishlistByUser(String userName);
}
