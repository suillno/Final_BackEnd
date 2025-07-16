package kr.co.kh.controller.game.member;


import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import kr.co.kh.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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
            @RequestParam String userName,
            @RequestParam int logType
            ) {
        try {
            GameWalletVO vo = new GameWalletVO();
            vo.setUserId(userId);
            vo.setBalance(amount);
            vo.setUserName(userName);
            vo.setLogType(logType);
            log.info("test{}", vo);
             String res = walletService.toggleGameWallet(vo);
            return ResponseEntity.ok(res);
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 요청:" + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류 발생");
        }
    }

    // 마이프로필 지갑내역 로그 표시
    @GetMapping("/logs/{userId}")
    public ResponseEntity<List<GameWalletLogVO>> selectLogsByUserId(@PathVariable Long userId) {
        List<GameWalletLogVO> logs = walletService.selectLogsByUserId(userId);
        return ResponseEntity.ok(logs);

    }

}

