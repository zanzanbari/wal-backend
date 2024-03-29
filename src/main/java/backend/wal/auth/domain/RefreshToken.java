package backend.wal.auth.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String value;

    private LocalDateTime expiredAt;

    private RefreshToken(final Long userId, final String value, final LocalDateTime expiredAt) {
        this.userId = userId;
        this.value = value;
        this.expiredAt = expiredAt;
    }

    public static RefreshToken newInstance(final Long userId, final String value, final Date expiredDate) {
        LocalDateTime expiredAt = expiredDate.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        return new RefreshToken(userId, value, expiredAt);
    }

    public void updateRefreshTokenValueAndExpiredAt(String value, Date expiredDate) {
        LocalDateTime expiredAt = expiredDate.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        this.value = value;
        this.expiredAt = expiredAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getValue() {
        return value;
    }
}
