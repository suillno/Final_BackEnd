package kr.co.kh.controller.game.member;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 로그인한 사용자의 비밀번호를 검증하고 회원 탈퇴 처리합니다.
     * @param request 요청으로 전달받은 회원 정보(비밀번호 포함)
     * @param user 현재 로그인한 사용자 정보
     * @return 탈퇴 처리 결과 메시지 (성공/실패)
     */
    @ApiOperation(value = "회원 탈퇴", notes = "로그인된 사용자의 비밀번호를 확인하고 계정을 비활성화합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "회원 탈퇴 요청 정보(MemberVO, 비밀번호 포함)", required = true, dataType = "MemberVO", paramType = "body")
    })
    @PostMapping("/leave")
    public ResponseEntity<?> leave(@RequestBody MemberVO request, @CurrentUser CustomUserDetails user) {

        request.setEmail(user.getEmail());
        request.setUsername(user.getUsername());
        log.info("입력 비밀번호: " + request.getPassword());

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!matches) {
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

        return ResponseEntity.ok(Map.of("message", "회원 탈퇴가 완료되었습니다."));
    }
}
