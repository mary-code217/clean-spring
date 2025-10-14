package org.toy.inflearn.adapter.integration;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;
import org.toy.inflearn.domain.Email;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {
    
    @Test
    @StdIo
    void 더미_이메일_전송(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("abc@naver.com"), "제목", "본문");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender.send: Email[address=abc@naver.com]");
    }

}