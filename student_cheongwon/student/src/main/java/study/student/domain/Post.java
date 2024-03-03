package study.student.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.student.dto.PostRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private LocalDate createdAt;

    private int agreeCnt;

    private int disagreeCnt;

    private String writer;  //member의 이름 별도 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Board board;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> commentList;

    /*
        Post 생성 메서드
     */
    public static Post createPost(PostRequest postRequest, Member member) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        if(postRequest.getBoard()==null){
            post.setBoard(Board.ETC);   //게시판 지정이 없으면 기타 게시판으로
        }else{
            post.setBoard(postRequest.getBoard());
        }
        post.setCreatedAt(LocalDate.now());
        post.setMember(member);
        post.setWriter(member.getName());
        post.setAgreeCnt(0);
        post.setDisagreeCnt(0);
        return post;
    }
}
