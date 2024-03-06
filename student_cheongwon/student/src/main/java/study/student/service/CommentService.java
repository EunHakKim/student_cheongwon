package study.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.Comment;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.dto.CommentRequest;
import study.student.repository.CommentPagingRepository;
import study.student.repository.CommentRepository;
import study.student.repository.PostPagingRepository;
import study.student.repository.PostRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentPagingRepository commentPagingRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment writeComment(CommentRequest commentRequest, Member member, Long postId) {
        Post post = postRepository.findOne(postId);
        if(post==null){
            throw new IllegalStateException("찾는 게시물이 없습니다.");
        }
        Comment comment = Comment.createComment(commentRequest, member, post);
        commentRepository.save(comment);
        post.setCommentCnt(post.getCommentCnt()+1);
        return comment;
    }

    public Comment findOne(Long id) {
        return commentRepository.findOne(id);
    }

    public Page<Comment> findAllByPost(Pageable pageable, Long postId) {
        Post post = postRepository.findOne(postId);
        return commentPagingRepository.findAllByPost(pageable,post);
    }
}
