package kr.co.kh.service.game.service;

import kr.co.kh.model.vo.GameCartVO;

public interface GameMemberService {
    // 장바구니저장
    void cartSave(GameCartVO vo);
}
