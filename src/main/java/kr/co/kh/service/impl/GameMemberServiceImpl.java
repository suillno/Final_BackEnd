package kr.co.kh.service.impl;

import kr.co.kh.mapper.GameMemberMapper;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.GameCartVO;
import kr.co.kh.model.vo.GameDiscountVO;
import kr.co.kh.model.vo.GameLikeVO;
import kr.co.kh.model.vo.GameReviewVO;
import kr.co.kh.service.GameMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     /** 장바구니 리스트 */
     @Override
     public List<GameCartVO> getCartByUser(String userName) {
          return gameMemberMapper.getCartByUser(userName);
     }
     /** 찜 리스트 */
     @Override
     public List<GameLikeVO> getWishlistByUser(String userName) {
          return gameMemberMapper.getWishlistByUser(userName);
     }


}
