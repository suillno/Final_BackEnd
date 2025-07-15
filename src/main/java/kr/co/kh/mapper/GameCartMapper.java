package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameCartMapper {
    // 라이브러리 저장
    String saveGameLibrary(GameCartVO vo);
    // 프로시저 사용 장바구니
    String toggleGameCart(GameCartVO vo);
    // 유저 장바구니 전체 목록 조회
    List<GameCartVO> getCartByUser(String userName);
    // 카트 상태체크
    int checkCart(GameCartVO vo);
    // 라이브러리 조회
    List<GameLibraryVO> getAllLibraryByUser(String userName);
}
