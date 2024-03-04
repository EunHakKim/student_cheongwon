package study.student.TestData;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.*;
import study.student.dto.CommentRequest;
import study.student.dto.JoinRequest;
import study.student.dto.PostRequest;
import study.student.service.CommentService;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class TestData {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    @PostConstruct
    public void init() {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("ADMIN");
        joinRequest.setStudentId("C123456");
        joinRequest.setPassword("1234");
        joinRequest.setStudentYear(StudentYear.Senior);
        joinRequest.setMajor(Major.Engineering);

        memberService.join(joinRequest);

        Optional<Member> findMember = memberService.findByStudentId("C123456");
        if (findMember.isEmpty()){
            throw new IllegalStateException("회원이 저장되지 않았습니다");
        }
        Member member = findMember.get();


        for (Board board : Board.values()){
            for(int i=1;i<12;i++){
                PostRequest postRequest = new PostRequest();
                postRequest.setTitle(board.name()+"Test"+i);
                postRequest.setContent(board.name()+"Test"+i);
                postRequest.setBoard(board);
                postService.writePost(postRequest, member);
            }
        }
    }
}
