package org.toy.inflearn.domain;

import org.junit.jupiter.api.Test;
import org.toy.inflearn.domain.shared.Email;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {
    
    @Test
    void 동등성() {
        var email1 = new Email("abc@naver.com");
        var email2 = new Email("abc@naver.com");
        
        assertThat(email1).isEqualTo(email2);
    }

}