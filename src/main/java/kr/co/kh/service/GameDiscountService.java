package kr.co.kh.service;

import kr.co.kh.model.vo.GameDiscountVO;
import kr.co.kh.model.vo.GameGroupVO;

import java.util.Map;

public interface GameDiscountService {
    // 게임할인 조회하기
    Map<String, Object> getDiscountList(Long page);
    // 게임할인 상태조회
    boolean checkDiscount(Long gameId);
    // 프로시저사용 할인가적용
    String toggleDiscount(GameDiscountVO vo);
    // 공동구매 등록
    void groupReservation(GameGroupVO vo);

}
