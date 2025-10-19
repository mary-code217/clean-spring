package org.toy.inflearn.application.member.provided;

import jakarta.validation.Valid;
import org.toy.inflearn.domain.member.Member;
import org.toy.inflearn.domain.member.MemberInfoUpdateRequest;
import org.toy.inflearn.domain.member.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
