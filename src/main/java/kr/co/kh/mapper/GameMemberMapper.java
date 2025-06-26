package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameCartVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameMemberMapper {
    // 장바구니저장
    void cartSave(GameCartVO vo);
}
