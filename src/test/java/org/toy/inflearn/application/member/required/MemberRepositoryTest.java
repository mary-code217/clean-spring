package org.toy.inflearn.application.member.required;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.toy.inflearn.domain.member.Member;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.toy.inflearn.domain.MemberFixture.createMemberRegisterRequest;
import static org.toy.inflearn.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void 멤버_생성() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }

    @Test
    void 중복이메일_가입실패() {
        Member member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member1);

        Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

}