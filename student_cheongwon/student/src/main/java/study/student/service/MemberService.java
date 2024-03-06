package study.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import study.student.dto.JoinRequest;
import study.student.domain.Member;
import study.student.dto.LoginRequest;
import study.student.repository.MemberRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(JoinRequest joinRequest) {
        Member member = Member.createMember(joinRequest);
        memberRepository.save(member);
        return member;
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public Optional<Member> findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId);
    }

    public Member login(LoginRequest loginRequest) {
        Optional<Member> findMember = memberRepository.findByStudentId(loginRequest.getStudentId());
        if(findMember.isEmpty()) {
            return null;
        }
        Member member = findMember.get();
        if(!member.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }
        return member;
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        /* 유효성 및 중복 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
}
