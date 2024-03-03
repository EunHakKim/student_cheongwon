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
import study.student.dto.JoinRequest;
import study.student.dto.PostRequest;

import java.util.List;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired PostService postService;
    @Autowired MemberService memberService;

    @Test
    public void 게시물작성() throws Exception{
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

        //When
        Member member = memberService.join(joinRequest);
        Post post = postService.writePost(postRequest,member);

        //Then
        Post findPost = postService.findOne(post.getId());
        if(findPost==null) {
            throw new IllegalStateException("게시물이 저장되지 않았습니다");
        }
        Assertions.assertEquals(post,findPost);
    }

    @Test
    public void 게시물전체조회() throws Exception{
        //Given
        PostRequest postRequest1 = new PostRequest();
        postRequest1.setTitle("Post1");
        postRequest1.setContent("Content1");
        postRequest1.setBoard(Board.ETC);

        PostRequest postRequest2 = new PostRequest();
        postRequest2.setTitle("Post2");
        postRequest2.setContent("Content2");
        postRequest2.setBoard(Board.ETC);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Pageable pageable = PageRequest.of(0,100);

        //When
        Member member = memberService.join(joinRequest);
        Post post1 = postService.writePost(postRequest1, member);
        Post post2 = postService.writePost(postRequest2, member);

        //Then
        Page<Post> findPosts = postService.findAll(pageable);
        List<Post> posts = findPosts.getContent();
        org.assertj.core.api.Assertions.assertThat(posts).contains(post1,post2);
    }

    @Test
    public void 게시판으로게시물조회() throws Exception{
        //Given
        PostRequest postRequest1 = new PostRequest();
        postRequest1.setTitle("Post1");
        postRequest1.setContent("Content1");
        postRequest1.setBoard(Board.CAFETERIA);

        PostRequest postRequest2 = new PostRequest();
        postRequest2.setTitle("Post2");
        postRequest2.setContent("Content2");
        postRequest2.setBoard(Board.CAFETERIA);

        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setName("Kim");
        joinRequest.setStudentId("A123456");
        joinRequest.setPassword("1234");
        joinRequest.setMajor(Major.Engineering);
        joinRequest.setStudentYear(StudentYear.Freshman);

        Pageable pageable = PageRequest.of(0,100);

        //When
        Member member = memberService.join(joinRequest);
        Post post1 = postService.writePost(postRequest1, member);
        Post post2 = postService.writePost(postRequest2, member);

        //Then
        Page<Post> findPosts = postService.findAllByBoard(pageable,Board.CAFETERIA);
        List<Post> posts = findPosts.getContent();
        org.assertj.core.api.Assertions.assertThat(posts).contains(post1,post2);
    }
}