package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameDiscountVO;
import kr.co.kh.model.vo.GameGroupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GameDiscountMapper {
    // 할인게임 리스트 전체 목록 조회
    List<GameDiscountVO> getDiscountGame(Map page);
    // 할인게인 상태 체크
    int checkDiscount(GameDiscountVO vo);
    // 프로시저 사용 할인
    String toggleDiscount(GameDiscountVO vo);
    // 공동구매 신청 버튼동작
    void groupReservation(GameGroupVO vo);
}
