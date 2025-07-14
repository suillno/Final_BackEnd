package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameLibraryVO;

import java.util.List;

public interface GameCartService {
    // 라이브러리 저장
    String saveGameLibrary(GameCartVO vo);
    // 프로시저사용 장바구니
    String toggleGameCart(GameCartVO vo);
    // 유저 장바구니 전체 목록 조회
    List<GameCartVO> getCartByUser(String userName);
    // 카트 상태 확인
    boolean checkCart(Long gameId, CustomUserDetails user);


}
