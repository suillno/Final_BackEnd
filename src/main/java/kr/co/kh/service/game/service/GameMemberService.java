package kr.co.kh.service.game.service;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameReviewVO;

import java.util.List;

public interface GameMemberService {
    // 장바구니저장
    void cartSave(GameCartVO vo);
    void reviewSave(GameReviewVO vo);
    List<GameReviewVO> reviewListByGameId(Long gameId);
}
