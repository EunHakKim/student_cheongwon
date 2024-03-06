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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.student.domain.Member;
import study.student.dto.LoginRequest;
import study.student.service.MemberService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/login")
public class MemberLoginController {

    private final MemberService memberService;
    private static String pastUri;  //로그인 이전 페이지로 이동 위한 static 변수

    @GetMapping
    public String createLoginRequest(Model model, HttpServletRequest request) {
        model.addAttribute("loginRequest", new LoginRequest());
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            pastUri=uri;
        }
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

        if (pastUri==null || pastUri.contains("/login")){
            return "redirect:/";
        } else{
            return "redirect:"+pastUri.substring(21);
        }

    }

    @GetMapping("/{studentId}")
    public String joinSuccess(@PathVariable("studentId")String studentId, Model model) {
        Optional<Member> findMember = memberService.findByStudentId(studentId);
        if(findMember.isEmpty()){
            throw new IllegalStateException("존재하지 않는 학번입니다");
        }
        model.addAttribute("name", findMember.get().getName());
        return "members/joinSuccess";
    }
}
