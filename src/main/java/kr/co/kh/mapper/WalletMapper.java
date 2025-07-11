package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameWalletVO;
import kr.co.kh.model.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface WalletMapper {
    MemberVO findMemberEmailById (Long userId);

    void toggleGameWallet(Map<String, Object> param);
}
