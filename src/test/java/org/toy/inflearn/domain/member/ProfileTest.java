package org.toy.inflearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void 프로필() {
        new Profile("anwnlro");
        new Profile("123456");
        new Profile("abc123");
        new Profile("");
    }

    @Test
    void 프로필_예외() {
        assertThatThrownBy(() -> new Profile("123456789101234568789")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("Aa")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        var profile = new Profile("anwnlro");

        assertThat(profile.url()).isEqualTo("@anwnlro");
    }

}