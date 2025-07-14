package kr.co.kh.service;

import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface GameMemberService {
    // 할인 포함 찜 목록 조회 (프로시저 기반)
    List<GameLikeVO> getDiscountWishlist(String userName);
    // 방문자 기록 저장
    void insertVisitorLog(Long userId);
}
