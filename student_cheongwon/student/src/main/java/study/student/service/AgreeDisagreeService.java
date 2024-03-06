package study.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.domain.PostAgree;
import study.student.domain.PostDisagree;
import study.student.repository.AgreeDisagreeRepository;
import study.student.repository.PostRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgreeDisagreeService {

    private final AgreeDisagreeRepository agreeDisagreeRepository;
    private final PostRepository postRepository;

    @Transactional
    public void agree(Long postId, Member member) {
        Post post = postRepository.findOne(postId);
        if(post==null) {
            throw new IllegalStateException("찾는 게시물이 없습니다.");
        }
        Optional<PostAgree> findPostAgree = agreeDisagreeRepository.findPostAgreeByPostAndMember(post, member);
        Optional<PostDisagree> findPostDisagree = agreeDisagreeRepository.findPostDisagreeByPostAndMember(post, member);

        if(findPostAgree.isEmpty() && findPostDisagree.isEmpty()) {
            PostAgree postAgree = PostAgree.createPostAgree(post, member);
            postAgree.setAgreeYn(true);
            PostDisagree postDisagree = PostDisagree.createPostDisagree(post, member);
            postDisagree.setDisagreeYn(false);
            agreeDisagreeRepository.save(postAgree,postDisagree);
            post.setAgreeCnt(post.getAgreeCnt()+1);
        }else if(findPostAgree.isPresent() && findPostDisagree.isPresent()){
            PostAgree postAgree = findPostAgree.get();
            PostDisagree postDisagree = findPostDisagree.get();
            if(postAgree.isAgreeYn() && !postDisagree.isDisagreeYn()) { //이미 찬성을 눌렀다면 취소를 수행
                postAgree.setAgreeYn(false);
                postDisagree.setDisagreeYn(false);
                post.setAgreeCnt(post.getAgreeCnt()-1);
            } else if(!postAgree.isAgreeYn() && postDisagree.isDisagreeYn()){ //이미 반대를 눌렀다면 찬성을 수행
                postAgree.setAgreeYn(true);
                postDisagree.setDisagreeYn(false);
                post.setAgreeCnt(post.getAgreeCnt()+1);
                post.setDisagreeCnt(post.getDisagreeCnt()-1);
            } else{
                postAgree.setAgreeYn(true);
                postDisagree.setDisagreeYn(false);
                post.setAgreeCnt(post.getAgreeCnt()+1);
            }
        } else{
            throw new IllegalStateException("찬성과 반대 객체에 이상이 있습니다.");
        }
    }

    @Transactional
    public void disagree(Long postId, Member member) {
        Post post = postRepository.findOne(postId);
        if(post==null) {
            throw new IllegalStateException("찾는 게시물이 없습니다.");
        }
        Optional<PostAgree> findPostAgree = agreeDisagreeRepository.findPostAgreeByPostAndMember(post, member);
        Optional<PostDisagree> findPostDisagree = agreeDisagreeRepository.findPostDisagreeByPostAndMember(post, member);

        if(findPostAgree.isEmpty() && findPostDisagree.isEmpty()) {//아직 찬성과 반대 객체가 없다면 새로 생성
            PostAgree postAgree = PostAgree.createPostAgree(post, member);
            postAgree.setAgreeYn(false);
            PostDisagree postDisagree = PostDisagree.createPostDisagree(post, member);
            postDisagree.setDisagreeYn(true);
            agreeDisagreeRepository.save(postAgree,postDisagree);
            post.setDisagreeCnt(post.getDisagreeCnt()+1);
        }else if(findPostAgree.isPresent() && findPostDisagree.isPresent()){ //이미 찬성과 반대 객체가 있다면 찾아서 변경
            PostAgree postAgree = findPostAgree.get();
            PostDisagree postDisagree = findPostDisagree.get();
            if(!postAgree.isAgreeYn() && postDisagree.isDisagreeYn()) { //이미 반대를 눌렀다면 취소를 수행
                postAgree.setAgreeYn(false);
                postDisagree.setDisagreeYn(false);
                post.setDisagreeCnt(post.getDisagreeCnt()-1);
            } else if(postAgree.isAgreeYn() && !postDisagree.isDisagreeYn()){ //이미 찬성을 눌렀다면 반대를 수행
                postAgree.setAgreeYn(false);
                postDisagree.setDisagreeYn(true);
                post.setAgreeCnt(post.getAgreeCnt()-1);
                post.setDisagreeCnt(post.getDisagreeCnt()+1);
            } else {
                postAgree.setAgreeYn(false);
                postDisagree.setDisagreeYn(true);
                post.setDisagreeCnt(post.getDisagreeCnt()+1);
            }
        } else{
            throw new IllegalStateException("찬성과 반대 객체에 이상이 있습니다.");
        }
    }

    public Boolean checkAgree(Long postId, Member member) {
        Post post = postRepository.findOne(postId);
        Optional<PostAgree> findPostAgree = agreeDisagreeRepository.findPostAgreeByPostAndMember(post, member);
        if(findPostAgree.isEmpty()){
            return null;
        }else return findPostAgree.get().isAgreeYn();
    }

    public Boolean checkDisagree(Long postId, Member member) {
        Post post = postRepository.findOne(postId);
        Optional<PostDisagree> findPostDisagree = agreeDisagreeRepository.findPostDisagreeByPostAndMember(post, member);
        if(findPostDisagree.isEmpty()) {
            return null;
        }else return findPostDisagree.get().isDisagreeYn();
    }
}