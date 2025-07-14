package kr.co.kh.controller.game.member;

// Swagger API 문서 작성을 위한 어노테이션
import io.swagger.annotations.*;

import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.*;

// 롬복 어노테이션
import kr.co.kh.service.GameCartService;
import kr.co.kh.service.GameDiscountService;
import kr.co.kh.service.GameLikeService;
import kr.co.kh.service.GameMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Spring MVC 관련 어노테이션
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

    @ApiOperation(
            value = "게임 상태 일괄 확인",
            notes = "좋아요, 장바구니, 할인 여부를 Map으로 반환")
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
     *  방문자 기록 API
     *
     *  기능 설명:
     * - 사용자가 메인 페이지에 접근할 때 호출되어,
     *   해당 사용자의 방문 기록을 `VISITOR_LOG` 테이블에 저장합니다.
     * - 단, 같은 날짜에 이미 방문 기록이 있는 경우 중복 저장되지 않음 (MERGE INTO 쿼리로 처리).
     * - 이 기록은 차트 통계(예: 일별 방문자 수, 누적 방문자 수 등)를 위한 데이터로 활용됩니다.
     *
     *  전제 조건:
     * - 사용자가 로그인되어 있어야 하며, 토큰 기반 인증을 통해 `@CurrentUser`로 사용자 정보를 주입받습니다.
     *
     *  요청 방식: POST
     *  요청 경로: /game/member/log/visit
     *  응답: 성공 시 "방문 기록 완료", 실패 시 500 에러 메시지 반환
     */
    @PostMapping("/log/visit")
    public ResponseEntity<?> logVisitor(@CurrentUser CustomUserDetails user) {
        try {
            // 현재 로그인한 사용자의 ID를 가져옴 (User 클래스를 상속받았기 때문에 바로 getId() 사용 가능)
            Long userId = user.getId();

            // 방문자 기록 INSERT (중복 방지를 위해 같은 날짜엔 한 번만 기록됨)
            gameMemberService.insertVisitorLog(userId);

            // 클라이언트에게 방문 기록 성공 메시지 반환
            return ResponseEntity.ok("방문 기록 완료");
        } catch (Exception e) {
            // 예외 발생 시 에러 로그 출력 및 500 응답
            log.error("방문자 기록 실패", e);
            return ResponseEntity.internalServerError().body("방문 기록 실패");
        }
    }

    // ✅ 로그인된 유저 프로필 조회
    @GetMapping(value = "/profile")
    @ApiOperation(value = "회원 정보 조회", notes = "현재 로그인한 사용자 정보를 반환")
    public ResponseEntity<?> getUserProfile(@CurrentUser CustomUserDetails user) {
        try {
            // 사용자 정보 조회
            UserVO userVO = gameMemberService.getUserInfo(user.getId());

            // 존재하지 않으면 404
            if (userVO == null) {
                return ResponseEntity.status(404).body(Map.of("error", "사용자 정보를 찾을 수 없습니다."));
            }

            // 성공 시 사용자 정보 반환
            return ResponseEntity.ok(userVO);

        } catch (Exception e) {
            log.error("회원 정보 조회 중 예외 발생", e);
            return ResponseEntity.status(500).body(Map.of("error", "서버 오류가 발생했습니다."));
        }
    }

    @ApiOperation(
            value = "회원 정보 수정",
            notes = "이메일과 프로필 이미지만 수정합니다."
    )
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

            // 🔒 이메일과 프로필 이미지만 남기고 나머지는 무시하거나 제거
            updatedVO.setUsername(null);     // username 수정 방지
            updatedVO.setBirth(null);        // 생일 수정 방지
            updatedVO.setRole(null);         // 역할 수정 방지
            updatedVO.setActive(null);       // 상태 수정 방지
            updatedVO.setEmailActive(null);  // 이메일 인증 상태 수정 방지
            updatedVO.setCreatedAt(null);    // 생성일 수정 방지

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

    @GetMapping("/profile-images")
    @ApiOperation(value = "프로필 이미지 목록", notes = "정적 폴더에 있는 프로필 이미지들을 URL 리스트로 반환합니다.")
    public ResponseEntity<List<String>> getProfileImages(HttpServletRequest req) {
        String base = ServletUriComponentsBuilder.fromRequestUri(req)
                .replacePath(null)
                .build()
                .toUriString(); // ex) http://localhost:8080

        List<String> imageUrls = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            imageUrls.add(base + "/profiles/profile_" + i + ".png"); // ✅ 언더스코어 추가!
        }

        return ResponseEntity.ok(imageUrls);
    }







}
