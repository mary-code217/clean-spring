package org.toy.inflearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.toy.inflearn.domain.MemberFixture.createMemberRegisterRequest;
import static org.toy.inflearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();

        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void 멤버_생성() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void 멤버_활성화() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void 멤버_활성화_실패() {
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 멤버_비화성화() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void 멤버_비활성화_실패() {
        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 비밀번호_검증() {
        assertThat(member.verifyPassword("1q2w3e4r", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void 닉네임_바꾸기() {
        assertThat(member.getNickname()).isEqualTo("abc");

        member.changeNickname("가나다");

        assertThat(member.getNickname()).isEqualTo("가나다");
    }

    @Test
    void 비밀번호_바꾸기() {
        member.changePassword("1q2w3e4r", passwordEncoder);
        
        assertThat(member.verifyPassword("1q2w3e4r", passwordEncoder)).isTrue();
    }

    @Test
    void 상태_확인() {
        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void 이메일주소_검증() {
        assertThatThrownBy(() -> Member.register(createMemberRegisterRequest("anwnlro"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

}