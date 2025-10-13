package org.toy.inflearn.application.provided;

import org.toy.inflearn.domain.Member;


/**
 * 회원을 조회한다.
 */
public interface MemberFinder {
    Member find(Long memberId);
}
