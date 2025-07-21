package kr.co.kh.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import kr.co.kh.annotation.CurrentUser;
import kr.co.kh.model.CustomUserDetails;
import kr.co.kh.model.vo.MenuVO;
import kr.co.kh.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 로그인한 사용자의 role 기준으로 접근 가능한 메뉴 목록을 조회합니다.
     * @param currentUser 현재 로그인한 사용자 정보
     * @return 권한에 따른 메뉴 리스트
     */
    @GetMapping("/list")
    @ApiOperation(value = "메뉴 목록 조회", notes = "로그인한 사용자의 권한(Role)에 따라 접근 가능한 메뉴 목록을 반환합니다.")
    @ApiImplicitParam(
            name = "currentUser",
            value = "현재 로그인한 사용자 정보",
            dataType = "CustomUserDetails",
            dataTypeClass = CustomUserDetails.class,
            paramType = "header",
            required = true
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('SYSTEM')")
    public ResponseEntity<?> menuList(@CurrentUser CustomUserDetails currentUser) {
        List<MenuVO> result = menuService.getList(currentUser);
        return ResponseEntity.ok(result);
    }
}