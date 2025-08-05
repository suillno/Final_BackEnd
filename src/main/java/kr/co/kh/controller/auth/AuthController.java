package kr.co.kh.controller.auth;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.exception.TokenRefreshException;
import kr.co.kh.exception.UserLoginException;
import kr.co.kh.exception.UserRegistrationException;
import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.payload.request.LoginRequest;
import kr.co.kh.model.payload.request.RegistrationRequest;
import kr.co.kh.model.payload.request.TokenRefreshRequest;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.payload.response.ApiResponse;
import kr.co.kh.model.payload.response.JwtAuthenticationResponse;
import kr.co.kh.model.token.RefreshToken;
import kr.co.kh.model.vo.MemberVO;
import kr.co.kh.security.JwtTokenProvider;
import kr.co.kh.service.AuthService;
import kr.co.kh.service.EmailAuthService;
import kr.co.kh.service.MailService;
import kr.co.kh.service.UserAuthorityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final MailService mailService;
    private final EmailAuthService emailAuthService;
    private final UserAuthorityService userAuthorityService;

    /**
     * 이메일 사용여부 확인
     */
    @ApiOperation(value = "이메일 사용여부 확인")
    @ApiImplicitParam(name = "email", value = "이메일", dataType = "String", required = true)
    @GetMapping("/check/email")
    public ResponseEntity<?> checkEmailInUse(@RequestParam("email") String email) {
        boolean emailExists = authService.emailAlreadyExists(email);
        return ResponseEntity.ok(new ApiResponse(true, emailExists ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다."));
    }

    /**
     * 인증 메일 발송
     */
    @PostMapping("/mail")
    @ApiOperation(value = "인증 메일 발송", notes = "이메일로 인증번호를 전송합니다.")
    @ApiImplicitParam(name = "emailRequest", value = "인증 메일 요청 정보", dataType = "EmailRequest", dataTypeClass = EmailRequest.class, required = true)
    public ResponseEntity<?> mail(@RequestBody EmailRequest emailRequest) {
        log.info(emailRequest.toString());
        mailService.sendMimeMessage(emailRequest);
        return ResponseEntity.ok("ok");
    }


    /**
     * 인증 코드 확인
     */
    @PostMapping("/mail/verify")
    @ApiOperation(value = "이메일 인증 확인", notes = "전송된 인증번호가 유효한지 확인합니다.")
    @ApiImplicitParam(name = "emailRequest", value = "이메일과 인증번호", dataType = "EmailRequest", dataTypeClass = EmailRequest.class, required = true)
    public ResponseEntity<?> verifyEmailCode(@RequestBody EmailRequest emailRequest) {
        boolean result = emailAuthService.verifyAuthCode(emailRequest.getMailTo(), emailRequest.getAuthCode());
        if (result) {
            emailAuthService.removeAuthCode(emailRequest.getMailTo());
            return ResponseEntity.ok("인증 성공!");
        } else {
            return ResponseEntity.badRequest().body("인증 실패. 인증번호가 틀렸거나 만료되었습니다.");
        }
    }

    /**
     * username 사용여부 확인
     */
    @ApiOperation(value = "아이디 사용여부 확인")
    @ApiImplicitParam(name = "username", value = "아이디", dataType = "String", required = true)
    @GetMapping("/check/username")
    public ResponseEntity<?> checkUsernameInUse(@RequestParam(
            "username") String username) {
        boolean usernameExists = authService.usernameAlreadyExists(username);
        return ResponseEntity.ok(new ApiResponse(true, usernameExists ? "이미 사용중인 아이디입니다." : "사용가능한 아이디 입니다."));
    }


    /**
     * 로그인 성공시 access token, refresh token 반환
     */
    @ApiOperation(value = "로그인")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "아이디", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "비밀번호", dataType = "String", required = true),
            @ApiImplicitParam(name = "deviceInfo", value = "장치정보", dataType = "DeviceInfo", required = true)
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("사용자 정보: {}", customUserDetails.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = authService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }



    /**
     * 특정 장치에 대한 refresh token 을 사용하여 만료된 jwt token 을 갱신 후 새로운 token 을 반환
     */
    @ApiOperation(value = "리프레시 토큰")
    @ApiImplicitParam(name = "refreshToken", value = "TokenRefreshRequest 객체", dataType = "String", required = true)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshJwtToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {

        log.info(tokenRefreshRequest.toString());

        return authService.refreshJwtToken(tokenRefreshRequest)
                .map(updatedToken -> {
                    String refreshToken = tokenRefreshRequest.getRefreshToken();
                    log.info("Created new Jwt Auth token: {}", updatedToken);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "토큰 갱신 중 오류가 발생했습니다. 다시 로그인 해 주세요."));
    }

    /**
     * 회원가입 요청을 처리합니다.
     * @param request 회원가입 폼 정보
     * @return 가입 성공 메시지 또는 예외
     */
    @PostMapping("/register")
    @ApiOperation(value = "회원가입", notes = "사용자로부터 회원가입 정보를 입력받아 계정을 생성합니다.")
    @ApiImplicitParam(name = "request", value = "회원가입 요청 객체", dataType = "RegistrationRequest", dataTypeClass = RegistrationRequest.class, required = true)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        log.info(request.toString());
        return authService.registerUser(request).map(user -> {
            return ResponseEntity.ok(new ApiResponse(true, "등록되었습니다."));
        }).orElseThrow(() -> new UserRegistrationException(request.getUsername(), "가입오류"));
    }

    /**
     * 사용자 이름과 이메일을 기반으로 아이디를 찾습니다.
     * @param memberVO 사용자 이름, 이메일 정보
     * @return 아이디 전송 결과 메시지
     */
    @PostMapping("/findId")
    @ApiOperation(value = "아이디 찾기", notes = "이름과 이메일 정보를 이용해 아이디를 조회하고 이메일로 전송합니다.")
    @ApiImplicitParam(name = "memberVO", value = "이름, 이메일 포함 VO 객체", dataType = "MemberVO", dataTypeClass = MemberVO.class, required = true)
    public ResponseEntity<?> findId(@RequestBody MemberVO memberVO) {
        MemberVO result = userAuthorityService.findId(memberVO);

        if (result != null && result.getUsername() != null) {
            return ResponseEntity.ok("아이디가 이메일로 전송되었습니다.");
        }
        return ResponseEntity.ok("일치하는 회원 정보를 찾을 수 없습니다.");
    }

    /**
     * 사용자의 비밀번호를 변경합니다. (아이디 찾기 후 초기화)
     * @param memberVO 비밀번호 변경 정보
     * @return 변경 성공 여부 메시지
     */
    @PostMapping("/changePw")
    @ApiOperation(value = "비밀번호 재설정", notes = "아이디 찾기 후 비밀번호를 새로 설정합니다.")
    @ApiImplicitParam(name = "memberVO", value = "비밀번호 변경 정보가 담긴 VO", dataType = "MemberVO", dataTypeClass = MemberVO.class, required = true)
    public ResponseEntity<?> changePw(@RequestBody MemberVO memberVO) {
        return ResponseEntity.ok(userAuthorityService.updatePw(memberVO));
    }

    /**
     * 로그인한 사용자의 비밀번호를 변경합니다.
     * @param body        현재 비밀번호, 새 비밀번호, 확인 비밀번호
     * @param userDetails 현재 로그인한 사용자 정보
     * @return 변경 결과 메시지
     */
    @PostMapping("/changePassword")
    @ApiOperation(value = "비밀번호 변경", notes = "로그인한 사용자가 본인의 비밀번호를 수정합니다.")
    @ApiImplicitParam(name = "body", value = "현재, 새, 확인 비밀번호 포함 Map", dataType = "Map", dataTypeClass = Map.class, required = true)
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body,
                                            @CurrentUser CustomUserDetails userDetails) {
        String currentPassword = body.get("currentPassword");
        String newPassword = body.get("newPassword");
        String confirmPassword = body.get("confirmPassword");

        MemberVO member = new MemberVO();
        member.setUsername(userDetails.getUsername());
        member.setPassword(currentPassword);

        try {
            userAuthorityService.changePassword(member, newPassword, confirmPassword);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 로그아웃 처리
     * @param userId
     * @return
     */
    @DeleteMapping("/logout/{userId}")
    public ResponseEntity<?> userLogout(@PathVariable int userId) {
        return ResponseEntity.ok(authService.userLogout(userId));
    }
}

