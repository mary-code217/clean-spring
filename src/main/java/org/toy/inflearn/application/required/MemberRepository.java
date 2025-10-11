package org.toy.inflearn.application.required;

import org.springframework.data.repository.Repository;
import org.toy.inflearn.domain.Member;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
}
