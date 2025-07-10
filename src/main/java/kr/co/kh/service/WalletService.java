package kr.co.kh.service;

import org.springframework.stereotype.Service;

@Service
public interface WalletService {
    // 인증코드 이메일 전송
    void sendAuthCode(Long userId);

    boolean verifyAuthCode(Long userId, String code);


}
