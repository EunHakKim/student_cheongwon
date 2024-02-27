package study.student.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import study.student.domain.Major;
import study.student.domain.Member;
import study.student.domain.MemberRole;
import study.student.domain.StudentYear;
import study.student.dto.JoinRequest;
import study.student.dto.LoginRequest;

import java.util.Optional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Test
    public void 회원가입() throws Exception {
        //Given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        //When
        memberService.join(joinRequest);

        //Then
        Optional<Member> findMember = memberService.findByStudentId(joinRequest.getStudentId());
        if(findMember.isEmpty()){
            throw new IllegalStateException("회원가입이 진행되지 않았습니다.");
        }
        Member member = findMember.get();
        Assertions.assertEquals(joinRequest.getName(),member.getName());
        Assertions.assertEquals(joinRequest.getStudentId(),member.getStudentId());
        Assertions.assertEquals(joinRequest.getPassword(),member.getPassword());
    }

    @Test
    public void 로그인() throws Exception {
        //Given
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        LoginRequest loginRequest1 = new LoginRequest();
        loginRequest1.setStudentId("B123456");
        loginRequest1.setPassword("12345");

        LoginRequest loginRequest2 = new LoginRequest();
        loginRequest2.setStudentId("A123456");
        loginRequest2.setPassword("1234");

        //When
        memberService.join(joinRequest);

        //Then
        Assertions.assertNull(memberService.login(loginRequest1));  //로그인 학번이나 비밀번호가 틀리면 null을 반환해야한다.
        Assertions.assertEquals(memberService.login(loginRequest2),memberService.findByStudentId(joinRequest.getStudentId()).get());
    }

}