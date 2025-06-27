package kr.co.kh.service.game.service;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameReviewVO;
import java.util.List;
import kr.co.kh.model.vo.GameLikeVO;

public interface GameMemberService {
    // 장바구니저장
    boolean cartSave(GameCartVO vo);
    // 리뷰 저장
    boolean reviewSave(GameReviewVO vo);
    // 리뷰리스트 조회
    List<GameReviewVO> reviewListByGameId(Long gameId);
    // 찜저장
    boolean likeSave(GameLikeVO vo);
}
