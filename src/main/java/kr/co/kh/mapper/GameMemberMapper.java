package kr.co.kh.mapper;

import kr.co.kh.model.vo.*;
import kr.co.kh.model.vo.GameLikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GameMemberMapper {
    void getDiscountWishlist(Map<String, Object> paramMap);
}
