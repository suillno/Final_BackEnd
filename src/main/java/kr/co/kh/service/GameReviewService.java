package kr.co.kh.service;

import kr.co.kh.model.vo.GameReviewVO;

import java.util.List;

public interface GameReviewService {
    // 리뷰 저장
    boolean reviewSave(GameReviewVO vo);
    // 리뷰 삭제
    int reviewDelete(String userName, Long gameId);
    // 리뷰리스트 조회
    List<GameReviewVO> reviewListByGameId(Long gameId);
}
