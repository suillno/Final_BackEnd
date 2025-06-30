package kr.co.kh.service.game.impl;

import kr.co.kh.mapper.GameMemberMapper;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameReviewVO;
import kr.co.kh.model.vo.GameLikeVO;
import kr.co.kh.service.game.service.GameMemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GameMemberServiceImpl implements GameMemberService {
    final private GameMemberMapper gameMemberMapper;

    // 리뷰 저장
    public boolean reviewSave(GameReviewVO vo) {
        int count = gameMemberMapper.countReview(vo);
        if (count > 0) {
            gameMemberMapper.reviewUpdate(vo); // 이미 있으면 업데이트
            return false;
        } else {
            gameMemberMapper.reviewSave(vo); // 없으면 새로 저장
            return true;
        }
    }
    // 리뷰 리스트
    public List<GameReviewVO> reviewListByGameId(Long gameId) { return gameMemberMapper.reviewListByGameId(gameId);}

    // 프로시저 사용 찜 저장
    @Override
    public String toggleGameLike(GameLikeVO vo) {
        gameMemberMapper.toggleGameLike(vo); // 이 호출에서 vo.result 필드가 채워짐
        return vo.getResult(); // 그대로 리턴
    }

    @Override
    public String toggleGameCart(GameCartVO vo) {
        gameMemberMapper.toggleGameCart(vo); // 이 호출에서 vo.result 필드가 채워짐
        return vo.getResult(); // 그대로 리턴
    }


}
