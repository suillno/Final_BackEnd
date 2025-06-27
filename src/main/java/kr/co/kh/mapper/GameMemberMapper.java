package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameReviewVO;
import kr.co.kh.model.vo.GameLikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameMemberMapper {
    // 장바구니저장
    void cartSave(GameCartVO vo);
    void reviewSave(GameReviewVO vo);
    List<GameReviewVO> reviewListByGameId(Long gameId);
    // 찜저장
    void likeSave(GameLikeVO vo);
    // 중복체크
    int countLike(GameLikeVO vo);
}
