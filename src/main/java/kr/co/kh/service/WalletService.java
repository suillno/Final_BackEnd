package kr.co.kh.service;

import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public interface WalletService {
    // 인증코드 이메일 전송
    void sendAuthCode(Long userId);

    boolean verifyAuthCode(Long userId, String code);

    // 지갑 충전
    void chargeWallet(Long userId, Long amount);

    String toggleGameWallet(GameWalletVO vo);

}
