package backend.wal.app.user.domain.entity;

import com.google.common.base.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialInfo {

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type", nullable = false, length = 20)
    private SocialType socialType;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SocialInfo that = (SocialInfo) o;
        return Objects.equal(socialId, that.socialId) && socialType == that.socialType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(socialId, socialType);
    }
}
