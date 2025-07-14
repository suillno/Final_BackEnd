package kr.co.kh.controller.auth;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * 메인 인증기능
     * 호출방법
     * http://localhost:8080/api/auth/mail?mailTo=song6115@naver.com&subject=인증메일&mailType=emailAuth
     * @param emailRequest
     * @return
     */
    @PostMapping("/mail")
    public ResponseEntity<?> mail(@RequestBody EmailRequest emailRequest) {
        log.info(emailRequest.toString());
        log.info("메일 요청 들어옴: {}", emailRequest);
        log.info("받는 사람: {}", emailRequest.getMailTo());
        mailService.sendMimeMessage(emailRequest);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/mail/verify")
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
        return ResponseEntity.ok(new ApiResponse(true, usernameExists ? "이미 사용중인 아이디입니다.": "사용가능한 아이디 입니다."));
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
     * 회원 가입
     * @param request
     * @return
     */
    @ApiOperation(value = "회원가입")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "아이디", dataType = "String", required = true),
            @ApiImplicitParam(name = "email", value = "이메일", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "비밀번호", dataType = "String", required = true),
            @ApiImplicitParam(name = "name", value = "이름", dataType = "String", required = true)
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        log.info(request.toString());
        return authService.registerUser(request).map(user -> {
            return ResponseEntity.ok(new ApiResponse(true, "등록되었습니다."));
        }).orElseThrow(() -> new UserRegistrationException(request.getUsername(), "가입오류"));
    }

    @PostMapping("/findId")
    public ResponseEntity<?> findId(@RequestBody MemberVO memberVO) {
        MemberVO result = userAuthorityService.findId(memberVO);

         if ( result != null && result.getUsername() != null) {

               return ResponseEntity.ok("아이디가 이메일로 전송되었습니다.");
         }
            return ResponseEntity.ok("일치하는 회원 정보를 찾을 수 없습니다.");
      }

    @PostMapping("/changePw")
    public ResponseEntity<?> changePw(@RequestBody MemberVO memberVO) {

        return ResponseEntity.ok(userAuthorityService.updatePw(memberVO));
    }

}
