package kr.co.kh.service.impl;

import kr.co.kh.mapper.GameReivewMapper;
import kr.co.kh.model.vo.GameReviewVO;
import kr.co.kh.service.GameReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameReviewServiceImpl implements GameReviewService {

    private final GameReivewMapper gameReivewMapper;

    // 리뷰 저장
    public boolean reviewSave(GameReviewVO vo) {
        int count = gameReivewMapper.countReview(vo);
        log.info("도달",vo.toString());
        if (count > 0) {
            gameReivewMapper.reviewUpdate(vo); // 이미 있으면 업데이트
            return false;
        } else {
            gameReivewMapper.reviewSave(vo); // 없으면 새로 저장
            return true;
        }
    }
    // 리뷰 삭제
    public int reviewDelete(String userName, Long gameId) {
        return gameReivewMapper.deleteReview(userName, gameId);
    }
    // 리뷰 리스트
    public List<GameReviewVO> reviewListByGameId(Long gameId) { return gameReivewMapper.reviewListByGameId(gameId);}
}
