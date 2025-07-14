package kr.co.kh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private Boolean active;
    private Boolean emailActive;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private LocalDateTime birth;
    private String profileImg;

}

