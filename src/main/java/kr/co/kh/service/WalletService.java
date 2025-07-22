package kr.co.kh.service;

import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface WalletService {
    // 인증코드 이메일 전송
    void sendAuthCode(Long userId);

    boolean verifyAuthCode(Long userId, String code);

    String toggleGameWallet(GameWalletVO vo);

    List<GameWalletLogVO> selectLogsByUserId(Long userId);

    List<GameWalletLogVO> listWallet(Long userId);

}
