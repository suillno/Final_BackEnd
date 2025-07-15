package kr.co.kh.service.impl;

import kr.co.kh.mapper.GameCartMapper;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;
import kr.co.kh.service.GameCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameCartServiceImpl implements GameCartService {
    private final GameCartMapper gameCartMapper;

    @Override
    public String saveGameLibrary(GameCartVO vo) {
        return gameCartMapper.saveGameLibrary(vo);
    }

    // 프로시저 사용 카트 저장
    @Override
    public String toggleGameCart(GameCartVO vo) {
        gameCartMapper.toggleGameCart(vo); // 이 호출에서 vo.result 필드가 채워짐
        return vo.getResult(); // 그대로 리턴
    }

    // 카트 리스트가져오기
    @Override
    public List<GameCartVO> getCartByUser(String userName) {
        return gameCartMapper.getCartByUser(userName);
    }
    
    // 카트상태 체크
    @Override
    public boolean checkCart(Long gameId, CustomUserDetails user) {
        GameCartVO vo = new GameCartVO();
        vo.setGameId(gameId);
        vo.setUserName(user.getUsername());
        // gameMemberMapper에서 COUNT(*)나 VO 여부를 확인하는 메서드 호출
        return gameCartMapper.checkCart(vo) > 0;
    }

    // 라이브러리 리스트 가져오기
     @Override
    public List<GameLibraryVO> getAllLibraryByUser(String userName) {
        return gameCartMapper.getAllLibraryByUser(userName);
    }
}
