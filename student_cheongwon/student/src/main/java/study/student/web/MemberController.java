package study.student.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.student.domain.Member;
import study.student.service.MemberService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{studentId}")
    public String joinSuccess(@PathVariable("studentId")String studentId, Model model) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if(findMember.isEmpty()){
            throw new IllegalStateException("존재하지 않는 학번입니다");
        }
        model.addAttribute("name", findMember.get().getName());
        return "members/joinSuccess";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        String uri = request.getHeader("Referer");
        if (uri == null) {
            return "redirect:/";
        }
        return "redirect:"+uri.substring(21);
    }
}
