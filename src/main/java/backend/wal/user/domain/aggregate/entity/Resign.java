package backend.wal.user.domain.aggregate.entity;

import backend.wal.user.domain.aggregate.converter.ResignReasonsConverter;
import backend.wal.user.domain.aggregate.ResignReason;
import backend.wal.user.domain.aggregate.ResignReasons;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "JSON", nullable = false)
    @Convert(converter = ResignReasonsConverter.class)
    private ResignReasons reasons;

    private Resign(final Long userId, final ResignReasons reasons) {
        this.userId = userId;
        this.reasons = reasons;
    }

    public static Resign newInstance(final Long userId, final Set<ResignReason> reasons) {
        return new Resign(userId, new ResignReasons(reasons));
    }
}
