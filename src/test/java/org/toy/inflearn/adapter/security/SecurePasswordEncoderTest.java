package org.toy.inflearn.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePasswordEncoderTest {
    
    @Test
    void 패스워드_시큐리티_테스트() {
        SecurePasswordEncoder encoder = new SecurePasswordEncoder();

        String passwordHash = encoder.encode("1q2w3e4r");
        
        assertThat(encoder.matches("1q2w3e4r", passwordHash)).isTrue();
        assertThat(encoder.matches("1q2w3e4r5t", passwordHash)).isFalse();
    }

}