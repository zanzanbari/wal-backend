package backend.wal.user.domain.aggregate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ResignReasons {

    private Set<ResignReason> reasons;

    public ResignReasons(final Set<ResignReason> reasons) {
        this.reasons = reasons;
    }
}
