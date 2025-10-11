package org.toy.inflearn.domain;

public record MemberCreateRequest(String email, String nickname, String password) {
}
