package kr.co.kh.controller.game.member;


import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.MemberVO;
import kr.co.kh.service.LeaveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/game")
@Slf4j
@AllArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    private final PasswordEncoder passwordEncoder;

// 탈퇴기능
    @PostMapping("/leave")
    public ResponseEntity<?> leave(@RequestBody MemberVO request, @CurrentUser CustomUserDetails user) {

        request.setEmail(user.getEmail());
        request.setUsername(user.getUsername());
        log.info("입력 비밀번호: " + request.getPassword());

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!matches) {
            // ❗ 메시지를 JSON으로 응답
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "비밀번호가 일치하지 않습니다."));
        }

        boolean success = leaveService.deactivateMember(user.getId(), request.getPassword());

        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "회원 탈퇴 처리 중 문제가 발생했습니다."));
        }

        // 성공 시도 마찬가지로 message 키로 응답
        return ResponseEntity.ok(Map.of("message", "회원 탈퇴가 완료되었습니다."));
    }

}
