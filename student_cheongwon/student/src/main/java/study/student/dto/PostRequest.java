package study.student.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import study.student.domain.Board;

@Getter
@Setter
public class PostRequest {
    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;

    private Board board;
}
