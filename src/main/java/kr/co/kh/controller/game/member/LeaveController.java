package kr.co.kh.controller.game.member;


import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.MemberVO;
import kr.co.kh.service.LeaveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.Map;


@RestController
@RequestMapping("/game")
@Slf4j
@AllArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;
    private final PasswordEncoder passwordEncoder;


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

    // 1. request 파라미터 3개 출력
    // 2. db에서 입력받은 파라미터로 데이터 행 조회 select * 요렇게
    // 3. request.getPassword() 메서드로 읽은 비번을 2행에서 조회한 비번과 동일한지 비교
    //      비교는 아래 코드 참조
    // passwordEncoder.matches(request.getPassword(), "select로 가져온 비번");
    // 이 메서드를 실행하면 true false로 떨어진다.
    // 4. false면 exception 처리
    // 5. true면 탈퇴 처리

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
//        }
//
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long memberId = userDetails.getId(); // ❗ CustomUserDetails에 getter 있어야 함
//
//        boolean success = leaveService.deactivateMember(memberId, request.getPassword());
//
//        if (success) {
//            return ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
//        }

}
