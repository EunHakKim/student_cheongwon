package study.student.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty(message = "학번을 입력하세요.")
    private String studentId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;
}
