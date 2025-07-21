package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 사용자 정보를 담는 VO 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "회원 기본 정보 VO")
public class UserVO {

    @ApiModelProperty(value = "회원 고유 ID", example = "1001")
    private Long userId;

    @ApiModelProperty(value = "사용자 아이디", example = "gamer123")
    private String username;

    @ApiModelProperty(value = "이메일 주소", example = "gamer123@example.com")
    private String email;

    @ApiModelProperty(value = "권한(Role)", example = "USER")
    private String role;

    @ApiModelProperty(value = "계정 활성화 여부", example = "true")
    private Boolean active;

    @ApiModelProperty(value = "이메일 인증 여부", example = "true")
    private Boolean emailActive;

    @ApiModelProperty(value = "계정 생성 일시", example = "2025-07-21 13:15:30")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "생년월일", example = "1995-03-25T00:00:00")
    private LocalDateTime birth;

    @ApiModelProperty(value = "프로필 이미지 URL", example = "https://example.com/images/profile.png")
    private String profileImg;
}
