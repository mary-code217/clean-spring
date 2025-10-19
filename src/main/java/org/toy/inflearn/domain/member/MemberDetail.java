package org.toy.inflearn.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import org.toy.inflearn.domain.AbstractEntity;

import java.time.LocalDateTime;
import java.util.Objects;

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_MEMBER_DETAIL_PROFILE_ADDRESS", columnNames = "profile_address")
})
@Entity
@Getter
@ToString(callSuper=true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    @Embedded
    private Profile profile;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    void setActivateAt() {
        Assert.isTrue(this.activatedAt == null, "이미 activatedAt은 설정되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        Assert.isTrue(this.deactivatedAt == null, "이미 deactivatedAt은 설정되었습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
