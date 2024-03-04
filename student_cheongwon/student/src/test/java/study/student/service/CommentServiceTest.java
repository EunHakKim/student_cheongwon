package study.student.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.*;
import study.student.dto.CommentRequest;
import study.student.dto.JoinRequest;
import study.student.dto.PostRequest;

import java.util.List;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired PostService postService;
    @Autowired MemberService memberService;
    @Autowired CommentService commentService;

    @Test
    public void 댓글작성(){
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

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setContent("Content1");

        //When
        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);
        Comment comment = commentService.writeComment(commentRequest, member, post);

        //Then
        Comment findComment = commentService.findOne(comment.getId());
        if(findComment==null) {
            throw new IllegalStateException("댓글이 저장되지 않았습니다");
        }
        Assertions.assertEquals(comment,findComment);
    }

    @Test
    public void 게시물로댓글찾기(){
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

        CommentRequest commentRequest1 = new CommentRequest();
        commentRequest1.setContent("Content1");

        CommentRequest commentRequest2 = new CommentRequest();
        commentRequest2.setContent("Content2");

        Pageable pageable = PageRequest.of(0,100);

        //When
        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);
        Comment comment1 = commentService.writeComment(commentRequest1, member, post);
        Comment comment2 = commentService.writeComment(commentRequest2, member, post);

        //Then
        Page<Comment> findComments = commentService.findAllByPost(pageable, post);
        List<Comment> comments = findComments.getContent();
        org.assertj.core.api.Assertions.assertThat(comments).contains(comment1,comment2);
    }
}