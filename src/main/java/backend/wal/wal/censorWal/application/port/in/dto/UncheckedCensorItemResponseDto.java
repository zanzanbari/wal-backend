package backend.wal.wal.censorWal.application.port.in.dto;

public class UncheckedCensorItemResponseDto {

    private final Long censorItemId;
    private final String walCategoryType;
    private final String contents;

    public UncheckedCensorItemResponseDto(Long censorItemId, String walCategoryType, String contents) {
        this.censorItemId = censorItemId;
        this.walCategoryType = walCategoryType;
        this.contents = contents;
    }

    public Long getCensorItemId() {
        return censorItemId;
    }

    public String getWalCategoryType() {
        return walCategoryType;
    }

    public String getContents() {
        return contents;
    }
}
