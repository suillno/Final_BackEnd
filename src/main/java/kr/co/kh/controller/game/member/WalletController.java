package kr.co.kh.controller.game.member;


import kr.co.kh.model.vo.GameWalletVO;
import kr.co.kh.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

    // 포인트 중천요청
    @PostMapping("/charge")
    public ResponseEntity<String> chargeWallet(
            @RequestParam Long userId,
            @RequestParam Long amount,
            @RequestParam String userName
            ) {
        try {
            GameWalletVO vo = new GameWalletVO();
            vo.setUserId(userId);
            vo.setBalance(amount);
            vo.setUserName(userName);
            vo.setLogType(0);

             String res = walletService.toggleGameWallet(vo);



            return ResponseEntity.ok(res);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 요청:" + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류 발생");
        }
    }

}

