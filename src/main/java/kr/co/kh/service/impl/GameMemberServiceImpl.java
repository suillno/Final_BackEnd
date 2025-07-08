package kr.co.kh.service.impl;

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

     // 리뷰 저장
     public boolean reviewSave(GameReviewVO vo) {
          int count = gameMemberMapper.countReview(vo);
          if (count > 0) {
               gameMemberMapper.reviewUpdate(vo); // 이미 있으면 업데이트
               return false;
          } else {
               gameMemberMapper.reviewSave(vo); // 없으면 새로 저장
               return true;
          }
     }
     // 리뷰 리스트
     public List<GameReviewVO> reviewListByGameId(Long gameId) { return gameMemberMapper.reviewListByGameId(gameId);}

     // 프로시저 사용 찜 저장
     @Override
     public String toggleGameLike(GameLikeVO vo) {
          gameMemberMapper.toggleGameLike(vo); // 이 호출에서 vo.result 필드가 채워짐
          return vo.getResult(); // 그대로 리턴
     }

     // 프로시저 사용 카트 저장
     @Override
     public String toggleGameCart(GameCartVO vo) {
          gameMemberMapper.toggleGameCart(vo); // 이 호출에서 vo.result 필드가 채워짐
          return vo.getResult(); // 그대로 리턴
     }

     // 프로시저 사용 카트 저장
     @Override
     public String toggleDiscount(GameDiscountVO vo) {
          gameMemberMapper.toggleDiscount(vo); // 이 호출에서 vo.result 필드가 채워짐
          return vo.getResult(); // 그대로 리턴
     }

     // 사용여부확인 아이콘 색상변경
     @Override
     public boolean checkLike(Long gameId, CustomUserDetails user) {
          GameLikeVO vo = new GameLikeVO();
          vo.setGameId(gameId);
          vo.setUserName(user.getUsername());
          // gameMemberMapper에서 COUNT(*)나 VO 여부를 확인하는 메서드 호출
          return gameMemberMapper.checkLike(vo) > 0;
     }
     public boolean checkCart(Long gameId, CustomUserDetails user) {
          GameCartVO vo = new GameCartVO();
          vo.setGameId(gameId);
          vo.setUserName(user.getUsername());
          // gameMemberMapper에서 COUNT(*)나 VO 여부를 확인하는 메서드 호출
          return gameMemberMapper.checkCart(vo) > 0;
     }
     public boolean checkDiscount(Long gameId) {
          GameDiscountVO vo = new GameDiscountVO();
          vo.setGameId(gameId);
          // gameMemberMapper에서 COUNT(*)나 VO 여부를 확인하는 메서드 호출
          return gameMemberMapper.checkDiscount(vo) > 0;
     }
     // 카트 리스트가져오기
     @Override
     public List<GameCartVO> getCartByUser(String userName) {
          return gameMemberMapper.getCartByUser(userName);
     }
     /** 찜 리스트 */
     @Override
     public List<GameLikeVO> getWishlistByUser(String userName) {
          return gameMemberMapper.getWishlistByUser(userName);
     }

     // 할인게임 리스트 가져오기
     @Override
     public Map<String, Object> getDiscountList(Long page) {
          Map<String, Object> discountList = new HashMap<>();

          // page → offset으로 변환
          int offset = Math.toIntExact(page * 20L);

          Map<String, Object> param = new HashMap<>();
          param.put("offset", offset);

          // 할인게임리스트 반환
          discountList.put("list", gameMemberMapper.getDiscountGame(param));
          return discountList;
     }
     
     // 할인게임 버튼 동작
     @Override
     public void groupReservation(GameGroupVO vo) {
          gameMemberMapper.groupReservation(vo);
          log.info(vo.toString());
     }

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




}
