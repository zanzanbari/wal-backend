package backend.wal.notification.domain.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String value;

    private FcmToken(final Long userId, final String value) {
        this.userId = userId;
        this.value = value;
    }

    public static FcmToken newInstance(final Long userId, final String value) {
        return new FcmToken(userId, value);
    }

    public String getValue() {
        return value;
    }
}
