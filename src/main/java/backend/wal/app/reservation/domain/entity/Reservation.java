package backend.wal.app.reservation.domain.entity;

import backend.wal.app.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String message;

    private LocalDateTime sendDueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ShowStatus showStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SendStatus sendStatus;
}
