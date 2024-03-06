package study.student.TestData;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.*;
import study.student.dto.CommentRequest;
import study.student.dto.JoinRequest;
import study.student.dto.PostRequest;
import study.student.service.AgreeDisagreeService;
import study.student.service.CommentService;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class TestData {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;
    private final AgreeDisagreeService agreeDisagreeService;

    @PostConstruct
    public void init() {
        for(int i=1;i<7;i++) {
            JoinRequest joinRequest = new JoinRequest();
            joinRequest.setName("Member"+i);
            joinRequest.setStudentId("B00000"+i);
            joinRequest.setPassword("1234");
            joinRequest.setStudentYear(StudentYear.Senior);
            joinRequest.setMajor(Major.Engineering);
            memberService.join(joinRequest);
        }

        Optional<Member> findMember = memberService.findByStudentId("B000001");
        Member member = findMember.get();
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("소프트웨어공학 과목 정원");
        postRequest.setContent("전공 필수 과목인 소프트웨어공학 과목의 정원을 늘려주실 것을 건의합니다.");
        postRequest.setBoard(Board.LECTURE);
        Post post = postService.writePost(postRequest, member);
        for(int i=1;i<12;i++){
            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setContent("동의합니다!! Test"+i);
            commentService.writeComment(commentRequest,member,post.getId());
        }

        Optional<Member> findMember2 = memberService.findByStudentId("B000002");
        Member member2 = findMember2.get();
        PostRequest postRequest2 = new PostRequest();
        postRequest2.setTitle("일체형 의자 개선 방향");
        postRequest2.setContent("학생들의 건강을 위해서 강의실의 일체형 의자를 바꿔주실 것을 건의합니다.");
        postRequest2.setBoard(Board.FACILITY);
        Post post2 = postService.writePost(postRequest2, member2);
        for(int i=1;i<15;i++){
            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setContent("동의합니다!! Test"+i);
            commentService.writeComment(commentRequest,member,post2.getId());
        }

        Optional<Member> findMember3 = memberService.findByStudentId("B000003");
        Member member3 = findMember3.get();
        PostRequest postRequest3 = new PostRequest();
        postRequest3.setTitle("인프런 홍익대 제휴");
        postRequest3.setContent("강의 사이트 인프런과 홍익대의 제휴를 건의드립니다.");
        postRequest3.setBoard(Board.WELFARE);
        Post post3 = postService.writePost(postRequest3, member3);
        for(int i=1;i<6;i++){
            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setContent("동의합니다!! Test"+i);
            commentService.writeComment(commentRequest,member2,post3.getId());
        }

        Optional<Member> findMember4 = memberService.findByStudentId("B000004");
        Member member4 = findMember4.get();
        PostRequest postRequest4 = new PostRequest();
        postRequest4.setTitle("학식 개선 방향");
        postRequest4.setContent("홍익대 학식도 메뉴 선택이 가능하면 좋겠습니다.");
        postRequest4.setBoard(Board.CAFETERIA);
        Post post4 = postService.writePost(postRequest4, member4);
        for(int i=1;i<3;i++){
            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setContent("동의합니다!! Test"+i);
            commentService.writeComment(commentRequest,member3,post4.getId());
        }

        Optional<Member> findMember5 = memberService.findByStudentId("B000005");
        Member member5 = findMember5.get();
        PostRequest postRequest5 = new PostRequest();
        postRequest5.setTitle("기숙사 건조기 건의");
        postRequest5.setContent("기숙사 추가 건조기 도입을 건의합니다.");
        postRequest5.setBoard(Board.DORMITORY);
        Post post5 = postService.writePost(postRequest5, member5);
        for(int i=1;i<19;i++){
            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setContent("동의합니다!! Test"+i);
            commentService.writeComment(commentRequest,member4,post5.getId());
        }

        Optional<Member> findMember6 = memberService.findByStudentId("B000006");
        Member member6 = findMember6.get();
        PostRequest postRequest6 = new PostRequest();
        postRequest6.setTitle("홍익대 홈페이지 건의");
        postRequest6.setContent("홍익대 홈페이지 색상 변경을 건의합니다.");
        postRequest6.setBoard(Board.ETC);
        Post post6 = postService.writePost(postRequest6, member6);
        for(int i=1;i<8;i++){
            CommentRequest commentRequest = new CommentRequest();
            commentRequest.setContent("동의합니다!! Test"+i);
            commentService.writeComment(commentRequest,member5,post6.getId());
        }

        agreeDisagreeService.agree(post.getId(),member);
        agreeDisagreeService.agree(post.getId(),member2);
        agreeDisagreeService.agree(post.getId(),member3);
        agreeDisagreeService.agree(post.getId(),member4);
        agreeDisagreeService.agree(post.getId(),member5);
        agreeDisagreeService.agree(post.getId(),member6);

        agreeDisagreeService.agree(post2.getId(),member2);
        agreeDisagreeService.agree(post2.getId(),member3);
        agreeDisagreeService.agree(post2.getId(),member4);
        agreeDisagreeService.agree(post2.getId(),member5);
        agreeDisagreeService.agree(post2.getId(),member6);

        agreeDisagreeService.agree(post3.getId(),member3);
        agreeDisagreeService.agree(post3.getId(),member4);
        agreeDisagreeService.agree(post3.getId(),member5);
        agreeDisagreeService.agree(post3.getId(),member6);

        agreeDisagreeService.agree(post4.getId(),member4);
        agreeDisagreeService.agree(post4.getId(),member5);
        agreeDisagreeService.agree(post4.getId(),member6);


        agreeDisagreeService.agree(post5.getId(),member5);
        agreeDisagreeService.agree(post5.getId(),member6);

        agreeDisagreeService.agree(post6.getId(),member6);
    }
}

