package study.student.Validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import study.student.domain.Member;
import study.student.dto.JoinRequest;
import study.student.repository.MemberRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CheckMemberValidator extends AbstractValidator<JoinRequest> {

    private final MemberRepository memberRepository;

    protected void doValidate(JoinRequest joinRequest, Errors errors) {
        Optional<Member> findMember = memberRepository.findByStudentId(joinRequest.getStudentId());
        if(!findMember.isEmpty()) {
            errors.rejectValue("studentId", "학번 중복 오류", "이미 사용중인 학번 입니다.");
        }
    }
}
