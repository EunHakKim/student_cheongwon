package study.student.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.student.domain.Comment;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.dto.CommentRequest;
import study.student.service.AgreeDisagreeService;
import study.student.service.CommentService;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;
    private final AgreeDisagreeService agreeDisagreeService;

    @PostMapping("/post/{postId}")
    public String comment(@Valid CommentRequest commentRequest, BindingResult result, Model model, Errors errors,
                          @SessionAttribute(name = "studentId", required = false) String studentId,
                          @PathVariable("postId") Long postId,
                          @PageableDefault(page = 0, size = 10, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Post findPost = postService.findOne(postId);
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (findMember.isEmpty()) {
            return "redirect:/members/login";
        }
        Member member = findMember.get();
        if(result.hasErrors()) {
            Boolean agreeYn = agreeDisagreeService.checkAgree(postId, member);
            Boolean disagreeYn = agreeDisagreeService.checkDisagree(postId, member);
            model.addAttribute("agreeYn",agreeYn);
            model.addAttribute("disagreeYn",disagreeYn);
            model.addAttribute("name", member.getName());

            Page<Comment> findComments = commentService.findAllByPost(pageable, postId);

            int nowPage = findComments.getPageable().getPageNumber() + 1;
            int startPage = Math.max(nowPage - 4, 1);
            int endPage = Math.min(nowPage + 9, findComments.getTotalPages());

            model.addAttribute("findComments", findComments);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            model.addAttribute("post", findPost);
            model.addAttribute("commentRequest", commentRequest);
            return "post/post";
        }
        commentService.writeComment(commentRequest,member,postId);
        return "redirect:/post/"+postId;

    }
}
