package study.student.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.service.AgreeDisagreeService;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AgreeDisagreeController {

    private final MemberService memberService;
    private final AgreeDisagreeService agreeDisagreeService;
    private final PostService postService;

    @GetMapping("/post/{postId}/agree")
    public String agree(@PathVariable("postId") Long postId, Model model,
                        @SessionAttribute(name = "studentId", required = false) String studentId) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (findMember.isEmpty()) {
            return "redirect:/members/login";
        }
        agreeDisagreeService.agree(postId,findMember.get());
        return "redirect:/post/"+ postId;
    }

    @GetMapping("/post/{postId}/disagree")
    public String disagree(@PathVariable("postId") Long postId, Model model,
                        @SessionAttribute(name = "studentId", required = false) String studentId) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if (findMember.isEmpty()) {
            return "redirect:/members/login";
        }
        agreeDisagreeService.disagree(postId,findMember.get());
        return "redirect:/post/"+ postId;
    }
}
