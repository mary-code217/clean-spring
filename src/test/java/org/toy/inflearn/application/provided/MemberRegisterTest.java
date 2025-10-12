package org.toy.inflearn.application.provided;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.toy.inflearn.SplearnTestConfiguration;
import org.toy.inflearn.domain.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
public record MemberRegisterTest(MemberRegister memberRegister) {

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
    void 멤버_생성_요청_검증실패() {
        extracted(new MemberRegisterRequest("abc@naver.com", "ab", "1q2w3e4r"));
        extracted(new MemberRegisterRequest("abcnaver.com", "ab", "1q2w3e4r"));
        extracted(new MemberRegisterRequest("abc@naver.com", "abgwefbadfghwergwefqsdadasdasd", "1q2w3e4r"));
        extracted(new MemberRegisterRequest("abc@naver.com", "abgwefbadfghwergwefqsdadasdasd", "1q"));
    }

    private void extracted(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
            .isInstanceOf(ConstraintViolationException.class);
    }


}
