package kr.co.kh.model.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 사용자 권한 변경 요청을 담는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRoleUpdateRequest {
    private Long userId;
    private String role;
}
