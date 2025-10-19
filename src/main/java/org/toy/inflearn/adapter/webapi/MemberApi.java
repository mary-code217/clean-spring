package org.toy.inflearn.adapter.webapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.toy.inflearn.adapter.webapi.dto.MemberRegisterResponse;
import org.toy.inflearn.application.member.provided.MemberRegister;
import org.toy.inflearn.domain.member.Member;
import org.toy.inflearn.domain.member.MemberRegisterRequest;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }

}
