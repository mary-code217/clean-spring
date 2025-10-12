package org.toy.inflearn.application.required;

import org.springframework.stereotype.Component;
import org.toy.inflearn.domain.Email;

/**
 * 이메일을 발송한다.
 */
public interface EmailSender {
    void send(Email email, String subject, String body);
}
