package org.toy.inflearn.domain;

import org.toy.inflearn.domain.member.MemberRegisterRequest;
import org.toy.inflearn.domain.member.PasswordEncoder;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "abc", "1q2w3e4r");
    }

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("abc@naver.com");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
