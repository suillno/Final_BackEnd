package kr.co.kh.model.payload.request;

import kr.co.kh.validation.annotation.NullOrNotBlank;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NullOrNotBlank(message = "아이디는 필수입니다.")
    private String username;

    @NullOrNotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;

    private String name;

    private String birth;

    private int roleNum;

}
