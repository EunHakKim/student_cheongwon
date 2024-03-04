package study.student.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.student.domain.Comment;
import study.student.domain.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long id) {
        return em.find(Comment.class,id);
    }

    public List<Comment> findAllByPost(Post post) {
        return em.createQuery("select c from Comment c where c.post=:post", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }
}
