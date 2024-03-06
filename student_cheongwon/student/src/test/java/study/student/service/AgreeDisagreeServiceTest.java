package study.student.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.*;
import study.student.dto.JoinRequest;
import study.student.dto.PostRequest;


@SpringBootTest
@Transactional
public class AgreeDisagreeServiceTest {

    @Autowired PostService postService;
    @Autowired MemberService memberService;
    @Autowired AgreeDisagreeService agreeDisagreeService;

    @Test
    public void 새로운게시물에찬성(){
        //Given
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Post1");
        postRequest.setContent("Content1");
        postRequest.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //When
        agreeDisagreeService.agree(post.getId(),member);

        //Then
        Assertions.assertEquals(post.getAgreeCnt(),1);
        Assertions.assertEquals(post.getDisagreeCnt(),0);
    }

    @Test
    public void 새로운게시물에반대(){
        //Given
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Post1");
        postRequest.setContent("Content1");
        postRequest.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //When
        agreeDisagreeService.disagree(post.getId(),member);

        //Then
        Assertions.assertEquals(post.getAgreeCnt(),0);
        Assertions.assertEquals(post.getDisagreeCnt(),1);
    }

    @Test
    public void 찬성게시물에찬성(){
        //Given
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Post1");
        postRequest.setContent("Content1");
        postRequest.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //When
        agreeDisagreeService.agree(post.getId(),member);
        agreeDisagreeService.agree(post.getId(),member);

        //Then
        Assertions.assertEquals(post.getAgreeCnt(),0);
        Assertions.assertEquals(post.getDisagreeCnt(),0);
    }

    @Test
    public void 반대게시물에반대(){
        //Given
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Post1");
        postRequest.setContent("Content1");
        postRequest.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //When
        agreeDisagreeService.disagree(post.getId(),member);
        agreeDisagreeService.disagree(post.getId(),member);

        //Then
        Assertions.assertEquals(post.getAgreeCnt(),0);
        Assertions.assertEquals(post.getDisagreeCnt(),0);
    }

    @Test
    public void 찬성게시물에반대(){
        //Given
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Post1");
        postRequest.setContent("Content1");
        postRequest.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //When
        agreeDisagreeService.agree(post.getId(),member);
        agreeDisagreeService.disagree(post.getId(),member);

        //Then
        Assertions.assertEquals(post.getAgreeCnt(),0);
        Assertions.assertEquals(post.getDisagreeCnt(),1);
    }

    @Test
    public void 반대게시물에찬성(){
        //Given
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Post1");
        postRequest.setContent("Content1");
        postRequest.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //When
        agreeDisagreeService.disagree(post.getId(),member);
        agreeDisagreeService.agree(post.getId(),member);

        //Then
        Assertions.assertEquals(post.getAgreeCnt(),1);
        Assertions.assertEquals(post.getDisagreeCnt(),0);
    }
}