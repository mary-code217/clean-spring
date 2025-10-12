package org.toy.inflearn.application.provided;

import jakarta.validation.Valid;
import org.toy.inflearn.domain.Member;
import org.toy.inflearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);
}
