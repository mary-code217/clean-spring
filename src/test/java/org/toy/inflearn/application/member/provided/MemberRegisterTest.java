package org.toy.inflearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.toy.inflearn.SplearnTestConfiguration;
import org.toy.inflearn.domain.member.*;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void 이메일_중복_실패() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void 멤버_활성화() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void 멤버_비활성화() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());
        
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void 업데이트() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        var request = new MemberInfoUpdateRequest("anwlnro", "anwnlro0001", "자기소개");
        member = memberRegister.updateInfo(member.getId(), request);

        assertThat(member.getDetail().getProfile().address()).isEqualTo("anwnlro0001");
    }
    
    @Test
    void 업데이트_실패() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("anwlnro", "anwnlro0001", "자기소개"));

        Member member2 = registerMember("anwnlro0002@naver.com");
        memberRegister.activate(member2.getId());
        entityManager.flush();
        entityManager.clear();

        // member2는 기존의 member와 같은 profile을 사용할 수 없다.
        assertThatThrownBy(() -> memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("anwnlro2", "anwnlro0001", "자기소개2")))
            .isInstanceOf(DuplicateProfileException.class);

        // 다른 프로필 주소로는 변경 가능
        memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("anwnlro2", "anwnlro0002", "자기소개2"));

        // 기존 프로필 주소를 바꾸는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("anwnlro", "anwnlro0001", "자기소개"));

        // 프로필 주소를 제거하는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("anwnlro", "", "자기소개"));

        // 프로필 주소 중복은 허용하지 않음
        assertThatThrownBy(() -> memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("anwnlro2", "anwnlro0002", "자기소개2")))
                .isInstanceOf(DuplicateProfileException.class);
    }

    @Test
    void 멤버_생성_요청_검증실패() {
        checkValidation(new MemberRegisterRequest("abc@naver.com", "ab", "1q2w3e4r"));
        checkValidation(new MemberRegisterRequest("abcnaver.com", "ab", "1q2w3e4r"));
        checkValidation(new MemberRegisterRequest("abc@naver.com", "abgwefbadfghwergwefqsdadasdasd", "1q2w3e4r"));
        checkValidation(new MemberRegisterRequest("abc@naver.com", "abgwefbadfghwergwefqsdadasdasd", "1q"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
            .isInstanceOf(ConstraintViolationException.class);
    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }


}
