package kr.co.kh.controller.game.member;

import io.swagger.annotations.*;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.*;
import kr.co.kh.service.GameCartService;
import kr.co.kh.service.GameDiscountService;
import kr.co.kh.service.GameLikeService;
import kr.co.kh.service.GameMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 게임 회원 기능을 처리하는 컨트롤러 클래스
 * 장바구니 등록 등 회원과 관련된 게임 처리 엔드포인트 정의
 */
@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/game/member") // 이 컨트롤러의 공통 URL Prefix
@Slf4j // 로그 사용을 위한 Lombok 어노테이션
@RequiredArgsConstructor // 생성자 주입을 위한 Lombok 어노테이션
public class GameMemberController {


    // 맴버 컨트롤 로직을 처리하는 서비스
    private final GameMemberService gameMemberService;
    private final GameCartService gameCartService;
    private final GameLikeService gameLikeService;
    private final GameDiscountService gameDiscountService;


    /**
     * 게임에 대한 좋아요, 장바구니, 할인 상태를 한 번에 확인합니다.
     * @param gameId 게임 ID
     * @param user 현재 로그인한 사용자 정보
     * @return 상태 Map(like, cart, discount)
     */
    @ApiOperation(value = "게임 상태 일괄 확인", notes = "좋아요, 장바구니, 할인 여부를 Map으로 반환")
    @GetMapping("/status/checkAll/{gameId}")
    public ResponseEntity<Map<String, Boolean>> checkStatus(
            @PathVariable Long gameId,
            @CurrentUser CustomUserDetails user) {

        Map<String, Boolean> statusMap = new HashMap<>();
        statusMap.put("like", gameLikeService.checkLike(gameId, user));
        statusMap.put("cart", gameCartService.checkCart(gameId, user));
        statusMap.put("discount", gameDiscountService.checkDiscount(gameId));

        return ResponseEntity.ok(statusMap);
    }

    /**
     * 로그인한 유저의 방문을 기록합니다.
     * @param user 현재 로그인한 사용자 정보
     * @return 방문 기록 완료 메시지
     */
    @PostMapping("/log/visit")
    public ResponseEntity<?> logVisitor(@CurrentUser CustomUserDetails user) {
        try {
            Long userId = user.getId();
            gameMemberService.insertVisitorLog(userId);
            return ResponseEntity.ok("방문 기록 완료");
        } catch (Exception e) {
            log.error("방문자 기록 실패", e);
            return ResponseEntity.internalServerError().body("방문 기록 실패");
        }
    }

    /**
     * 로그인한 사용자의 정보를 조회합니다.
     * @param user 현재 로그인한 사용자 정보
     * @return 사용자 정보(UserVO)
     */
    @GetMapping("/profile")
    @ApiOperation(value = "회원 정보 조회", notes = "현재 로그인한 사용자 정보를 반환")
    public ResponseEntity<?> getUserProfile(@CurrentUser CustomUserDetails user) {
        try {
            UserVO userVO = gameMemberService.getUserInfo(user.getId());
            if (userVO == null) {
                return ResponseEntity.status(404).body(Map.of("error", "사용자 정보를 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(userVO);
        } catch (Exception e) {
            log.error("회원 정보 조회 중 예외 발생", e);
            return ResponseEntity.status(500).body(Map.of("error", "서버 오류가 발생했습니다."));
        }
    }

    /**
     * 이메일과 프로필 이미지를 수정합니다.
     * @param updatedVO 수정할 사용자 정보
     * @return 수정 결과 메시지
     */
    @ApiOperation(value = "회원 정보 수정", notes = "이메일과 프로필 이미지만 수정합니다.")
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserVO updatedVO) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!(principal instanceof CustomUserDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "로그인이 필요합니다."));
            }

            CustomUserDetails userDetails = (CustomUserDetails) principal;
            updatedVO.setUserId(userDetails.getId());

            updatedVO.setUsername(null);
            updatedVO.setBirth(null);
            updatedVO.setRole(null);
            updatedVO.setActive(null);
            updatedVO.setEmailActive(null);
            updatedVO.setCreatedAt(null);

            boolean updated = gameMemberService.updateUserProfile(updatedVO);
            if (updated) {
                return ResponseEntity.ok("회원 정보가 수정되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정 실패 또는 변경 사항 없음.");
            }
        } catch (Exception e) {
            log.error("회원 정보 수정 실패", e);
            return ResponseEntity.internalServerError().body("회원 정보 수정 중 오류 발생");
        }
    }

    /**
     * 프로필 이미지 목록을 반환합니다.
     * @param req HttpServletRequest 객체
     * @return 프로필 이미지 URL 리스트
     */
    @ApiOperation(value = "프로필 이미지 목록", notes = "정적 폴더에 있는 프로필 이미지들을 URL 리스트로 반환합니다.")
    @GetMapping("/profile-images")
    public ResponseEntity<List<String>> getProfileImages(HttpServletRequest req) {
        String base = ServletUriComponentsBuilder.fromRequestUri(req)
                .replacePath(null)
                .build()
                .toUriString();

        List<String> imageUrls = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            imageUrls.add(base + "/profiles/profile_" + i + ".png");
        }

        return ResponseEntity.ok(imageUrls);
    }
}