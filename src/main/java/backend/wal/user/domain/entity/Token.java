package backend.wal.user.domain.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String fcmToken;

    private Token(final Long userId, final String fcmToken) {
        this.userId = userId;
        this.fcmToken = fcmToken;
    }

    public static Token newInstance(final Long userId, final String fcmToken) {
        return new Token(userId, fcmToken);
    }
}
