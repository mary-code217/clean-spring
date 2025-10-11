package org.toy.inflearn.domain;

public record MemberRegisterRequest(String email, String nickname, String password) {
}
