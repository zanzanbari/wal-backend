package backend.wal.user.domain.aggregate;

import com.google.common.base.Objects;

import java.util.Set;

public class ResignReasons {

    private Set<ResignReason> reasons;

    private ResignReasons() {
    }

    public ResignReasons(final Set<ResignReason> reasons) {
        this.reasons = reasons;
    }

    public Set<ResignReason> getReasons() {
        return reasons;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResignReasons that = (ResignReasons) o;
        return Objects.equal(reasons, that.reasons);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reasons);
    }

    @Override
    public String toString() {
        return "ResignReasons{" +
                "reasons=" + reasons +
                '}';
    }
}
