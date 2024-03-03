package study.student.domain;

import lombok.Getter;

@Getter
public enum Board {
    LECTURE("강의 관련 게시판"),
    FACILITY("시설 관련 게시판"),
    WELFARE("복지 관련 게시판"),
    DORMITORY("기숙사 관련 게시판"),
    CAFETERIA("학식 관련 게시판"),
    ETC("기타 게시판");

    private final String description;

    Board(String description) {
        this.description=description;
    }
}
