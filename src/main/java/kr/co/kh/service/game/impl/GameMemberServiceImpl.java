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

    @Override
    // 카트 저장
    public void cartSave(GameCartVO vo) {
        gameMemberMapper.cartSave(vo);
    }
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
    @Override
    public boolean likeSave(GameLikeVO vo) {
        // username, gameId 두가지로 select where절로 지정해서 카운트해보고 0개면 insert 0개 이상이면 다른 메시지로 유도
        int count = gameMemberMapper.countLike(vo);
        if (count == 0) {
            gameMemberMapper.likeSave(vo);
            return true;
        } else {
            return false;
        }

    }
}
