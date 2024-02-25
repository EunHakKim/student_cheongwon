package study.student.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String studentId;

    private String password;

    @Enumerated(EnumType.STRING)
    private Major major;

    private int studentYear;
}
