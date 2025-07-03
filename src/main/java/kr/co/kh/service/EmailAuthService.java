package kr.co.kh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class EmailAuthService {
    // 이메일 → 인증번호 저장
    private final ConcurrentHashMap<String, String> authCodeStorage = new ConcurrentHashMap<>();

    // 인증번호 저장
    public void saveAuthCode(String email, String code) {
        authCodeStorage.put(email, code);
        log.info("✅ 저장된 인증코드: [{}] -> [{}]", email, code);
    }

    // 인증번호 검증
    public boolean verifyAuthCode(String email, String code) {
        String savedCode = authCodeStorage.get(email);
        return savedCode != null && savedCode.equals(code);
    }

    // 인증번호 삭제 (성공 시)
    public void removeAuthCode(String email) {
        authCodeStorage.remove(email);
    }
}
