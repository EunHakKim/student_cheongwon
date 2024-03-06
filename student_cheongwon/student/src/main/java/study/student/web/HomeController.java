package study.student.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.student.domain.Member;
import study.student.domain.Post;
import study.student.service.MemberService;
import study.student.service.PostService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final PostService postService;

    @RequestMapping("/")
    public String home(Model model, @SessionAttribute(name = "studentId", required = false) String studentId) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if(!findMember.isEmpty()) {
            model.addAttribute("name", findMember.get().getName());
        }
        List<Post> findPostsByAgreeCnt = postService.findTop5ByAgreeCnt();
        List<Post> findPostsByCommentCnt = postService.findTop5ByCommentCnt();
        model.addAttribute("postsByAgreeCnt",findPostsByAgreeCnt);
        model.addAttribute("postsByCommentCnt",findPostsByCommentCnt);
        return "home";
    }
}
