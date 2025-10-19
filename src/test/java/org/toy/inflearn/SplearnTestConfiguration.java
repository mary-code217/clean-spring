package org.toy.inflearn;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.toy.inflearn.application.member.required.EmailSender;
import org.toy.inflearn.domain.member.MemberFixture;
import org.toy.inflearn.domain.member.PasswordEncoder;

@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("TestEmailSender.send: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
