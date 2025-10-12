package org.toy.inflearn.adapter.integration;

import org.springframework.stereotype.Component;
import org.toy.inflearn.application.required.EmailSender;
import org.toy.inflearn.domain.Email;

@Component
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender.send: "+ email);
    }
}
