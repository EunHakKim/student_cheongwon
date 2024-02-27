package study.student.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.student.dto.JoinRequest;

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

    @Enumerated(EnumType.STRING)
    private StudentYear studentYear;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    /*
        멤버 생성 메소드
     */
    public static Member createMember(JoinRequest joinRequest) {
        Member member = new Member();
        member.setName(joinRequest.getName());
        member.setStudentId(joinRequest.getStudentId());
        member.setPassword(joinRequest.getPassword());
        member.setMajor(joinRequest.getMajor());
        member.setStudentYear(joinRequest.getStudentYear());
        member.setMemberRole(MemberRole.User);
        return member;
    }
}
