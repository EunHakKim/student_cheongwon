package study.student.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.domain.PostAgree;
import study.student.domain.PostDisagree;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AgreeDisagreeRepository {

    private final EntityManager em;

    public void save(PostAgree postAgree, PostDisagree postDisagree) {
        em.persist(postAgree);
        em.persist(postDisagree);
    }

    public Optional<PostAgree> findPostAgreeByPostAndMember (Post post, Member member) {
        List<PostAgree> findPostAgree = em.createQuery("select p from PostAgree p where p.post=:post and p.member=:member", PostAgree.class)
                .setParameter("post", post)
                .setParameter("member", member)
                .getResultList();
        return findPostAgree.stream().findAny();
    }

    public Optional<PostDisagree> findPostDisagreeByPostAndMember (Post post, Member member) {
        List<PostDisagree> findPostDisagree = em.createQuery("select p from PostDisagree p where p.post=:post and p.member=:member", PostDisagree.class)
                .setParameter("post", post)
                .setParameter("member", member)
                .getResultList();
        return findPostDisagree.stream().findAny();
    }
}
