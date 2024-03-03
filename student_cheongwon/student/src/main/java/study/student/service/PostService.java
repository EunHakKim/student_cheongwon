package study.student.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.Board;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.dto.PostRequest;
import study.student.repository.MemberRepository;
import study.student.repository.PagingRepository;
import study.student.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PagingRepository pagingRepository;

    @Transactional
    public Post writePost(PostRequest postRequest, Member member) {
        Post post = Post.createPost(postRequest,member);
        postRepository.save(post);
        return post;
    }

    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    public Page<Post> findAll(Pageable pageable) {
        return pagingRepository.findAll(pageable);
    }

    public Page<Post> findAllByBoard(Pageable pageable, Board board) {
        return pagingRepository.findAllByBoard(pageable, board);
    }
}
