package kr.co.kh.service.impl;

import kr.co.kh.mapper.GameLikeMapper;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameLikeVO;
import kr.co.kh.service.GameLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameLikeSerivceImpl implements GameLikeService {

    private final GameLikeMapper gameLikeMapper;

    // 프로시저 사용 찜 저장
    @Override
    public String toggleGameLike(GameLikeVO vo) {
        gameLikeMapper.toggleGameLike(vo); // 이 호출에서 vo.result 필드가 채워짐
        return vo.getResult(); // 그대로 리턴
    }
    // 사용여부확인 아이콘 색상변경
    @Override
    public boolean checkLike(Long gameId, CustomUserDetails user) {
        GameLikeVO vo = new GameLikeVO();
        vo.setGameId(gameId);
        vo.setUserName(user.getUsername());
        // gameMemberMapper에서 COUNT(*)나 VO 여부를 확인하는 메서드 호출
        return gameLikeMapper.checkLike(vo) > 0;
    }
    /** 찜 리스트 */
    @Override
    public List<GameLikeVO> getWishlistByUser(String userName) {
        return gameLikeMapper.getWishlistByUser(userName);
    }

}
