package kr.co.kh.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.model.payload.request.UserRegisterRequest;
import kr.co.kh.model.payload.request.UserRoleUpdateRequest;
import kr.co.kh.model.vo.UserVO;
import kr.co.kh.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/game/member")
@Slf4j
@AllArgsConstructor
public class MemberController {

    private final UserService userService;

    /**
     * 관리자 권한으로 사용자 목록을 조회합니다.
     * @param searchType 검색 기준 (username, email, name)
     * @param searchKeyword 검색어
     * @return 조건에 맞는 사용자 목록
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('SYSTEM')")
    @ApiOperation(value = "사용자 목록 호출", notes = "검색 조건에 맞는 사용자 목록을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchType", value = "검색 구분", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "searchKeyword", value = "검색어", required = true, dataType = "String", paramType = "query")
    })
    public ResponseEntity<?> list(
            @RequestParam(value = "searchType", defaultValue = "") String searchType,
            @RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword
    ) {
        return ResponseEntity.ok(userService.userSearch(searchType, searchKeyword));
    }

    /**
     * 사용자 조회 시, 선택된 유저의 권한 목록을 조회합니다.
     * @param id 사용자 ID
     * @return 권한 목록
     */
    @GetMapping("/roleList")
    @PreAuthorize("hasRole('SYSTEM')")
    @ApiOperation(value = "사용자 권한 목록 조회", notes = "사용자 ID에 해당하는 권한 정보를 반환합니다.")
    @ApiImplicitParam(name = "id", value = "사용자 ID", required = true, dataType = "Long", paramType = "query")
    public ResponseEntity<?> roleList(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(userService.roleSearch(id));
    }

    /**
     * 관리자가 새로운 사용자를 등록합니다.
     * @param registrationRequest 등록 요청 정보
     * @return 생성된 사용자 정보 또는 상태 메시지
     */
    @PostMapping("/save")
    @PreAuthorize("hasRole('SYSTEM')")
    @ApiOperation(value = "사용자 생성", notes = "관리자가 새로운 사용자를 등록합니다.")
    @ApiImplicitParam(name = "registrationRequest", value = "사용자 등록 요청 정보", required = true, dataType = "UserRegisterRequest", paramType = "body")
    public ResponseEntity<?> save(@Valid @RequestBody UserRegisterRequest registrationRequest) {
        return ResponseEntity.ok(userService.saveUser(registrationRequest));
    }

    /**
     * 전체 사용자 목록을 조회합니다. (MyBatis + VO 기반)
     * @return 모든 사용자 VO 리스트
     */
    @GetMapping(value = "/vo-list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "전체 사용자 목록 조회 (VO 기반)", notes = "MyBatis VO 기반으로 전체 사용자 목록을 반환합니다.")
    public ResponseEntity<List<UserVO>> voList() {
        log.info("/api/member/vo-list 호출됨");
        List<UserVO> userList = userService.getAllUsersByMapper();
        return ResponseEntity.ok(userList);
    }

    /**
     * 사용자의 권한을 수정합니다.
     * @param request 사용자 ID 및 권한 정보
     * @return 권한 변경 성공 메시지
     */
    @PatchMapping("/update-role")
    @ApiOperation(value = "사용자 권한 수정", notes = "사용자의 권한 정보를 수정합니다.")
    @ApiImplicitParam(name = "request", value = "권한 수정 요청 정보", required = true, dataType = "UserRoleUpdateRequest", paramType = "body")
    public ResponseEntity<String> updateUserRole(@RequestBody UserRoleUpdateRequest request) {
        log.info(">>> 권한 변경 요청 수신: {}, {}", request.getUserId(), request.getRole());
        userService.updateUserRole(request.getUserId(), request.getRole());
        return ResponseEntity.ok("권한이 성공적으로 변경되었습니다.");
    }

    /**
     * 사용자의 상태를 정지 또는 활성으로 전환합니다.
     * @param userId 사용자 ID
     * @return 상태 변경 성공 메시지
     */
    @PatchMapping("/toggle-status")
    @ApiOperation(value = "사용자 계정 상태 토글", notes = "계정의 상태를 정지 또는 활성으로 전환합니다.")
    @ApiImplicitParam(name = "userId", value = "사용자 ID", required = true, dataType = "Long", paramType = "query")
    public ResponseEntity<String> toggleUserStatus(@RequestParam("userId") Long userId) {
        userService.toggleUserStatus(userId);
        return ResponseEntity.ok("사용자 상태가 변경되었습니다.");
    }
}