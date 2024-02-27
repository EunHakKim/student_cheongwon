package study.student.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.student.domain.Member;
import study.student.dto.LoginRequest;
import study.student.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/login")
public class MemberLoginController {

    private final MemberService memberService;

    @GetMapping
    public String createLoginRequest(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "members/createLoginRequest";
    }

    @PostMapping
    public String login(@Valid LoginRequest loginRequest, BindingResult result, Model model, Errors errors, HttpServletRequest request) {
        Member findmember = memberService.login(loginRequest);
        if(findmember==null) {
            model.addAttribute("loginFailure",1);
            return "members/createLoginRequest";
        }
        if(result.hasErrors()) {
            return "members/createLoginRequest";
        }

        request.getSession().invalidate();
        HttpSession session = request.getSession(true);

        session.setAttribute("studentId", loginRequest.getStudentId());
        session.setMaxInactiveInterval(900);//session을 15분간 유지

        return "redirect:/";
    }
}
