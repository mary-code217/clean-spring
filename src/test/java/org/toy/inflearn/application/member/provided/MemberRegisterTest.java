package org.toy.inflearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.toy.inflearn.SplearnTestConfiguration;
import org.toy.inflearn.domain.*;
import org.toy.inflearn.domain.member.DuplicateEmailException;
import org.toy.inflearn.domain.member.Member;
import org.toy.inflearn.domain.member.MemberRegisterRequest;
import org.toy.inflearn.domain.member.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void 이메일_중복_실패() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void 멤버_활성화() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void 멤버_생성_요청_검증실패() {
        checkValidation(new MemberRegisterRequest("abc@naver.com", "ab", "1q2w3e4r"));
        checkValidation(new MemberRegisterRequest("abcnaver.com", "ab", "1q2w3e4r"));
        checkValidation(new MemberRegisterRequest("abc@naver.com", "abgwefbadfghwergwefqsdadasdasd", "1q2w3e4r"));
        checkValidation(new MemberRegisterRequest("abc@naver.com", "abgwefbadfghwergwefqsdadasdasd", "1q"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
            .isInstanceOf(ConstraintViolationException.class);
    }


}
