package backend.wal.admin.domain.aggregate;

public enum Role {

    ADMIN("관리자"),
    ;

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
