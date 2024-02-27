package study.student.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import study.student.domain.Major;
import study.student.domain.StudentYear;

@Getter
@Setter
public class JoinRequest {

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "학번은 필수입니다.")
    private String studentId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private Major major;

    private StudentYear studentYear;
}
