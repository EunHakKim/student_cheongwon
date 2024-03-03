package study.student.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PostDisagree {

    @Id
    @GeneratedValue
    @Column(name = "disagree_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean disagreeYn;

    /*
    생성 메서드
     */

    public static PostDisagree createPostDisagree(Post post, Member member) {
        PostDisagree postDisagree = new PostDisagree();
        postDisagree.setPost(post);
        postDisagree.setMember(member);
        postDisagree.setDisagreeYn(false);
        return postDisagree;
    }
}
