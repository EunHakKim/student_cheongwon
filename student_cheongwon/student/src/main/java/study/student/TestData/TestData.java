package study.student.TestData;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.Board;
import study.student.domain.Major;
import study.student.domain.Member;
import study.student.domain.StudentYear;
import study.student.dto.JoinRequest;
import study.student.dto.PostRequest;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class TestData {

    private final MemberService memberService;
    private final PostService postService;

    @PostConstruct
    public void init() {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("김은학");
        joinRequest.setStudentId("B911042");
        joinRequest.setPassword("1234");
        joinRequest.setStudentYear(StudentYear.Senior);
        joinRequest.setMajor(Major.Engineering);

        memberService.join(joinRequest);

        Optional<Member> findMember = memberService.findByStudentId("B911042");
        if (findMember.isEmpty()){
            throw new IllegalStateException("회원이 저장되지 않았습니다");
        }
        Member member = findMember.get();


        for(int i=1;i<22;i++){
            PostRequest postRequest = new PostRequest();
            postRequest.setTitle("Test"+i);
            postRequest.setContent("Test"+i);
            postRequest.setBoard(Board.ETC);
            postService.writePost(postRequest, member);
        }

    }
}
