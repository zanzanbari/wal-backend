package backend.wal.support;

public enum Role {

    USER("사용자"),
    ADMIN("관리자"),
    ;

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
