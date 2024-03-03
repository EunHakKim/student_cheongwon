package study.student.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.student.domain.Board;
import study.student.domain.Post;

import java.awt.print.Pageable;
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

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findByBoard(Board board) {
        return em.createQuery("select p from Post p where p.board = :board", Post.class)
                .setParameter("board", board)
                .getResultList();
    }
}
