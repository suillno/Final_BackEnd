package kr.co.kh.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 회원 정보를 담는 VO 클래스
 */
@Getter
@Setter
@Slf4j
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "회원 정보를 담는 VO 클래스")
public class MemberVO {

    @ApiModelProperty(value = "회원 고유 ID", example = "1001")
    private Long memberId;

    @ApiModelProperty(value = "이메일 주소", example = "user@example.com")
    private String email;

    @ApiModelProperty(value = "이메일 인증 여부", example = "true")
    private boolean emailVerified;

    @ApiModelProperty(value = "로그인 아이디 (username)", example = "john_doe")
    private String username;

    @ApiModelProperty(value = "비밀번호 (암호화된 형태)", example = "Encrypted123!")
    private String password;

    @ApiModelProperty(value = "사용자 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(value = "계정 활성화 여부", example = "true")
    private boolean active;

    @ApiModelProperty(value = "회원 가입일", example = "2025-07-01T10:00:00")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "회원 정보 수정일", example = "2025-07-15T15:30:00")
    private LocalDateTime updatedAt;
}
