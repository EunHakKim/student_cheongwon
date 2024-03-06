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
import study.student.repository.PostPagingRepository;
import study.student.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostPagingRepository postPagingRepository;

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
        return postPagingRepository.findAll(pageable);
    }

    public Page<Post> findAllByBoard(Pageable pageable, Board board) {
        return postPagingRepository.findAllByBoard(pageable, board);
    }

    public List<Post> findTop5ByAgreeCnt() {
        return postRepository.findTop5ByAgreeCnt();
    }

    public List<Post> findTop5ByCommentCnt() {
        return postRepository.findTop5ByCommentCnt();
    }
}
