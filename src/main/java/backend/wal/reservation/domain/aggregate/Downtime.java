package backend.wal.reservation.domain.aggregate;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Downtime {

    private Long groupId;
    private LocalDateTime value;

    protected Downtime() {
    }

    public Downtime(Long groupId, LocalDateTime value) {
        this.groupId = groupId;
        this.value = value;
    }
}
