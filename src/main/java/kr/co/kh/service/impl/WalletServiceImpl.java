package kr.co.kh.service.impl;

import kr.co.kh.mapper.WalletMapper;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.vo.MemberVO;
import kr.co.kh.service.EmailAuthService;
import kr.co.kh.service.MailService;
import kr.co.kh.service.WalletService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletMapper walletMapper;
    private final MailService mailService;
    private final EmailAuthService emailAuthService;

    @Override
    public void sendAuthCode(Long userId) {
        MemberVO memberVO = walletMapper.findMemberEmailById(userId);

        if ( memberVO == null || memberVO.getEmail() == null) {
            log.error("사용자 정보 또는 이메일이 없습니다. userId={}", userId);
            throw new IllegalStateException("사용자 정보가 올바르지 않습니다.");
        }
        String email = memberVO.getEmail();

        String authCode = mailService.generateAuthCode(); // MailService 내 인증코드 생성 메서드

        // 3) 이메일 전송용 DTO 준비
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setMailTo(email);
        emailRequest.setUsername(memberVO.getUsername());
        emailRequest.setMailType("emailAuth");
        emailRequest.setAuthCode(authCode); // MailService에서 html에 이걸로 출력함
        emailRequest.setName(memberVO.getUsername());

        // 4) 인증코드 저장 (emailAuthService)
        emailAuthService.saveAuthCode(email, authCode);

        // 5) 메일 발송
        mailService.sendMimeMessage(emailRequest);

        log.info("인증코드 전송 완료. userId={}, email={}, code={}", userId, email, authCode);

    }

    // 인증코드 검증 (예시)
    @Override
    public boolean verifyAuthCode(Long userId, String code) {
        MemberVO member = walletMapper.findMemberEmailById(userId);

        if (member == null || member.getEmail() == null) {
            log.error("사용자 정보 또는 이메일이 없습니다. userId={}", userId);
            throw new IllegalStateException("사용자 정보가 올바르지 않습니다.");
        }

        String email = member.getEmail();

        boolean valid = emailAuthService.verifyAuthCode(email, code);
        if (valid) {
            emailAuthService.removeAuthCode(email);
        }
        return valid;
    }
}
