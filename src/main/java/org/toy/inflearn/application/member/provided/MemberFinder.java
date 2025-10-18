package org.toy.inflearn.application.member.provided;

import org.toy.inflearn.domain.member.Member;


/**
 * 회원을 조회한다.
 */
public interface MemberFinder {
    Member find(Long memberId);
}
