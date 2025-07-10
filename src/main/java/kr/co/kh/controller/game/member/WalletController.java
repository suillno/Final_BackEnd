package kr.co.kh.controller.game.member;


import kr.co.kh.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/game/wallet")
public class WalletController {

    private final WalletService walletService;

    // ✅ 이메일 인증코드 전송
    @PostMapping("/sendAuthCode")
    public ResponseEntity<Object> sendAuthCode(@RequestParam Long userId) {

        log.info("🔐 이메일 인증코드 요청: userId={}", userId);
        walletService.sendAuthCode(userId);
        return ResponseEntity.ok().build();
    }

    // ✅ 인증코드 검증
    @PostMapping("/verifyAuthCode")
    public ResponseEntity<?> verifyAuthCode(@RequestParam Long userId, @RequestParam String code) {
        log.info("🔍 인증코드 확인 요청: userId={}, code={}", userId, code);
        return ResponseEntity.ok(walletService.verifyAuthCode(userId, code));

    }
}

