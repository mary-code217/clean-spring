package org.toy.inflearn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.toy.inflearn.domain.Member;
import org.toy.inflearn.domain.MemberStatus;
import org.toy.inflearn.domain.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
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

        member = Member.create("abc@naver.com", "ABC", "secret", passwordEncoder);
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

    

}