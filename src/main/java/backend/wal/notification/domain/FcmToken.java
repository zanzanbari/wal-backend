package backend.wal.notification.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String value;

    @CreatedDate
    private LocalDateTime createdAt;

    private FcmToken(final Long userId, final String value) {
        this.userId = userId;
        this.value = value;
    }

    public static FcmToken newInstance(final Long userId, final String value) {
        return new FcmToken(userId, value);
    }

    public boolean isNotSameWith(String value) {
        return !this.value.equals(value);
    }

    public void updateTokenValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
