package org.toy.inflearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        member = Member.create(new MemberCreateRequest("abc@naver.com", "ABC", "secret"), passwordEncoder);
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
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void 닉네임_바꾸기() {
        assertThat(member.getNickname()).isEqualTo("ABC");

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
        assertThatThrownBy(() -> Member.create(new MemberCreateRequest("anwnlro", "abc", "1q2w3e4r"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

}