package study.student.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.student.domain.Board;
import study.student.domain.Post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository{

    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }

    public Post findOne(Long id) {
        return em.find(Post.class,id);
    }

    public List<Post> findTop5ByAgreeCnt() {
        return em.createQuery("select p from Post p order by p.agreeCnt desc ", Post.class)
                .setMaxResults(5)
                .getResultList();
    }

    public List<Post> findTop5ByCommentCnt() {
        return em.createQuery("select p from Post p order by p.commentCnt desc ", Post.class)
                .setMaxResults(5)
                .getResultList();
    }
}
