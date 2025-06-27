package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameMemberMapper {
    // 장바구니저장
    void cartSave(GameCartVO vo);
    void reviewSave(GameReviewVO vo);
    List<GameReviewVO> reviewListByGameId(Long gameId);
}
