package backend.wal.wal.censorWal.domain.aggregate;

public enum CheckStatus {

    APPROVED("승인"),
    REJECTED("거절"),
    UNCHECKED("미확인")
    ;

    private final String value;

    CheckStatus(String value) {
        this.value = value;
    }
}
