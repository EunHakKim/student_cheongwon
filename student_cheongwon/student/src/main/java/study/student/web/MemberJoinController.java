package study.student.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.student.Validator.CheckMemberValidator;
import study.student.dto.JoinRequest;
import study.student.service.MemberService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/join")
public class MemberJoinController {

    private final MemberService memberService;
    private final CheckMemberValidator checkMemberValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkMemberValidator);
    }

    @GetMapping
    public String joinRequest(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());
        return "members/createJoinRequest";
    }

    @PostMapping
    public String join(@Valid JoinRequest joinRequest, BindingResult result, Model model, Errors errors) {
        if (result.hasErrors()){
            model.addAttribute("joinRequest", joinRequest);

            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "members/createJoinRequest";
        }
        memberService.join(joinRequest);
        return "redirect:/members/login/" + joinRequest.getStudentId();
    }
}
