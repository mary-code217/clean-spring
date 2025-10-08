package org.toy.inflearn;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private Long id;

    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;




}
