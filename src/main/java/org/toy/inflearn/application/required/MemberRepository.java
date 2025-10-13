package org.toy.inflearn.application.required;

import org.springframework.data.repository.Repository;
import org.toy.inflearn.domain.Email;
import org.toy.inflearn.domain.Member;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);
}
