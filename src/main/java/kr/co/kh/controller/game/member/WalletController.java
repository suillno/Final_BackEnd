package kr.co.kh.controller.game.member;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.vo.GameWalletLogVO;
import kr.co.kh.model.vo.GameWalletVO;
import kr.co.kh.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/game/wallet")
public class WalletController {

    private final WalletService walletService;

    /**
     * 로그인한 사용자의 이메일로 인증코드를 전송합니다.
     * @param userId 사용자 ID
     * @return 200 OK 응답
     */
    @ApiOperation(value = "이메일 인증코드 전송", notes = "해당 유저의 이메일로 인증코드를 전송합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "long", paramType = "query")
    })
    @PostMapping("/sendAuthCode")
    public ResponseEntity<Object> sendAuthCode(@RequestParam Long userId) {
        log.info("🔐 이메일 인증코드 요청: userId={}", userId);
        walletService.sendAuthCode(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자가 입력한 인증코드를 검증합니다.
     * @param userId 사용자 ID
     * @param code 입력한 인증코드
     * @return 검증 결과(boolean 또는 메시지)
     */
    @ApiOperation(value = "이메일 인증코드 검증", notes = "사용자가 입력한 인증코드가 일치하는지 검증합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "인증코드", required = true, dataType = "string", paramType = "query")
    })
    @PostMapping("/verifyAuthCode")
    public ResponseEntity<?> verifyAuthCode(@RequestParam Long userId, @RequestParam String code) {
        log.info("🔍 인증코드 확인 요청: userId={}, code={}", userId, code);
        return ResponseEntity.ok(walletService.verifyAuthCode(userId, code));
    }

    /**
     * 사용자의 지갑에 포인트를 충전합니다.
     * @param userId 사용자 ID
     * @param amount 충전할 포인트 금액
     * @param userName 사용자 이름
     * @param logType 로그 유형 (1: 충전, 2: 사용 등)
     * @return 처리 결과 메시지
     */
    @ApiOperation(value = "지갑 포인트 충전", notes = "사용자의 지갑에 포인트를 충전합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "amount", value = "충전할 금액", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "사용자 이름", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "logType", value = "로그 타입(1:충전, 2:사용)", required = true, dataType = "int", paramType = "query")
    })
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
            log.info("충전 요청: {}", vo);
            String res = walletService.toggleGameWallet(vo);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("잘못된 요청:" + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 오류 발생");
        }
    }

    /**
     * 사용자 ID를 기준으로 지갑 내역을 조회합니다.
     * @param userId 사용자 ID
     * @return 지갑 거래 내역 리스트
     */
    @ApiOperation(value = "지갑 거래 로그 조회", notes = "사용자 ID를 기준으로 지갑 거래 내역을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "long", paramType = "path")
    })
    @GetMapping("/logs/{userId}")
    public ResponseEntity<List<GameWalletLogVO>> selectLogsByUserId(@PathVariable Long userId) {
        List<GameWalletLogVO> logs = walletService.selectLogsByUserId(userId);
        return ResponseEntity.ok(logs);
    }
}