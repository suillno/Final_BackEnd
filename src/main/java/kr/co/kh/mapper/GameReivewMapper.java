package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameReivewMapper {
    // 리뷰 존재 여부 확인 (중복 검사용)
    int countReview(GameReviewVO vo);
    // 리뷰 저장
    int reviewSave(GameReviewVO vo);
    // 리뷰 수정
    int reviewUpdate(GameReviewVO vo);
    // 리뷰 삭제
    int deleteReview(String userName, Long gameId);
    // 리뷰 리스트 조회
    List<GameReviewVO> reviewListByGameId(Long gameId);
}
