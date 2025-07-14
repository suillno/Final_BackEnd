package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameLikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameLikeMapper {
    // 프로시저 사용 찜
    String toggleGameLike(GameLikeVO vo);
    // 게임 좋아요 기능 데이터 있을시 색상 변경
    int checkLike(GameLikeVO vo);
    // 유저 찜(WISH) 목록 조회
    List<GameLikeVO> getWishlistByUser(String userName);
}
