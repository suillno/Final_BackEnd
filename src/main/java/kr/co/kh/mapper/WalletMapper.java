package kr.co.kh.mapper;

import kr.co.kh.model.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletMapper {
    MemberVO findMemberEmailById (Long userId);
}
