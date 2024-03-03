package study.student.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PostAgree {

    @Id @GeneratedValue
    @Column(name = "agree_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean agreeYn;

    /*
    생성 메서드
     */

    public static PostAgree createPostAgree(Post post, Member member) {
        PostAgree postAgree = new PostAgree();
        postAgree.setPost(post);
        postAgree.setMember(member);
        postAgree.setAgreeYn(false);
        return postAgree;
    }
}
