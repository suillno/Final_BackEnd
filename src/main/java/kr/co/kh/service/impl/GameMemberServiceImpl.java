package kr.co.kh.service.impl;

import kr.co.kh.mapper.ChartMapper;
import kr.co.kh.mapper.GameMemberMapper;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.*;
import kr.co.kh.service.GameMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameMemberServiceImpl implements GameMemberService {

     private final GameMemberMapper gameMemberMapper;
     private final ChartMapper chartMapper;

     // 할인 포함 찜 목록 조회 (프로시저 기반)
     @Override
     public List<GameLikeVO> getDiscountWishlist(String userName) {
          Map<String, Object> paramMap = new HashMap<>();
          paramMap.put("P_USERNAME", userName);
          paramMap.put("P_CURSOR", null); // OUT 파라미터 초기화

          gameMemberMapper.getDiscountWishlist(paramMap); // 프로시저 호출

          // 프로시저 결과가 들어있는 OUT 커서를 가져오기
          return (List<GameLikeVO>) paramMap.get("P_CURSOR");
     }

     @Override
     public void insertVisitorLog(Long userId) {
          chartMapper.insertVisitorLog(userId); // ChartMapper 사용
     }




}
