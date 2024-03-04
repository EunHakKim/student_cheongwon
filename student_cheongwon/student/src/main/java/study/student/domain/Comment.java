package study.student.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.student.dto.CommentRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    private LocalDate cratedAt;

    private String writer;  //member의 이름 별도 저장

    /*
    생성 메서드
     */
    public static Comment createComment(CommentRequest commentRequest, Member member, Post post) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setMember(member);
        comment.setPost(post);
        comment.setCratedAt(LocalDate.now());
        comment.setWriter(member.getName());
        return comment;
    }
}
