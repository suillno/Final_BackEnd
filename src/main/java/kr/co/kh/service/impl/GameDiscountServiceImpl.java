package kr.co.kh.service.impl;

import kr.co.kh.mapper.GameDiscountMapper;
import kr.co.kh.model.vo.GameDiscountVO;
import kr.co.kh.model.vo.GameGroupVO;
import kr.co.kh.service.GameDiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameDiscountServiceImpl implements GameDiscountService {

    private final GameDiscountMapper gameDiscountMapper;

    // 할인게임 리스트 가져오기
    @Override
    public Map<String, Object> getDiscountList(Long page) {
        Map<String, Object> discountList = new HashMap<>();

        // page → offset으로 변환
        int offset = Math.toIntExact(page * 20L);

        Map<String, Object> param = new HashMap<>();
        param.put("offset", offset);

        // 할인게임리스트 반환
        discountList.put("list", gameDiscountMapper.getDiscountGame(param));
        return discountList;
    }

    // 할인리스트 체크
    @Override
    public boolean checkDiscount(Long gameId) {
        GameDiscountVO vo = new GameDiscountVO();
        vo.setGameId(gameId);
        // gameMemberMapper에서 COUNT(*)나 VO 여부를 확인하는 메서드 호출
        return gameDiscountMapper.checkDiscount(vo) > 0;
    }

    // 프로시저 사용 카트 저장
    @Override
    public String toggleDiscount(GameDiscountVO vo) {
        gameDiscountMapper.toggleDiscount(vo); // 이 호출에서 vo.result 필드가 채워짐
        return vo.getResult(); // 그대로 리턴
    }

    // 할인게임 버튼 동작
    @Override
    public void groupReservation(GameGroupVO vo) {
        gameDiscountMapper.groupReservation(vo);
        log.info(vo.toString());
    }
}
