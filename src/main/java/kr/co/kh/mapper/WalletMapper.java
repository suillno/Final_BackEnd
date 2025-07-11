package kr.co.kh.mapper;

import kr.co.kh.model.vo.GameWalletVO;
import kr.co.kh.model.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface WalletMapper {
    MemberVO findMemberEmailById (Long userId);

    GameWalletVO selectWalletByUserId(Long userId);   // XML의 id="selectWalletByUserId"

    int updateBalance(GameWalletVO gameWalletVO);

    void toggleGameWallet(Map<String, Object> param);
}
