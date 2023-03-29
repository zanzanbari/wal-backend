package backend.wal.onboard.application.port.dto;

import backend.wal.onboard.domain.common.WalTimeTypes;

public final class WalTimeTypesResponseDto {

    private final WalTimeTypes willCancelAfterNow;
    private final WalTimeTypes willAddAfterNow;

    public WalTimeTypesResponseDto(final WalTimeTypes willCancelAfterNow, final WalTimeTypes willAddAfterNow) {
        this.willCancelAfterNow = willCancelAfterNow;
        this.willAddAfterNow = willAddAfterNow;
    }

    public WalTimeTypes getWillCancelAfterNow() {
        return willCancelAfterNow;
    }

    public WalTimeTypes getWillAddAfterNow() {
        return willAddAfterNow;
    }
}
