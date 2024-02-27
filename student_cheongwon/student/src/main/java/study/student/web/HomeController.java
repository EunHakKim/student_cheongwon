package study.student.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.student.domain.Member;
import study.student.service.MemberService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @RequestMapping("/")
    public String home(Model model, @SessionAttribute(name = "studentId", required = false) String studentId) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if(!findMember.isEmpty()) {
            model.addAttribute("name", findMember.get().getName());
        }
        return "home";
    }
}
