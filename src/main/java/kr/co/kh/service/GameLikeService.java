package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameLikeVO;

import java.util.List;

public interface GameLikeService {

    // 프로시저사용 찜
    String toggleGameLike(GameLikeVO vo);
    // 사용여부 표기 색상변경
    boolean checkLike(Long gameId, CustomUserDetails user);
    // 유저 찜(WISH) 목록 조회
    List<GameLikeVO> getWishlistByUser(String userName);
}
