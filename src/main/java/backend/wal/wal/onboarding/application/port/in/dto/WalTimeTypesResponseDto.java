package backend.wal.wal.onboarding.application.port.in.dto;

import backend.wal.wal.common.domain.WalTimeType;

import java.util.Set;

public final class WalTimeTypesResponseDto {

    private final Set<WalTimeType> willCancelAfterNow;
    private final Set<WalTimeType> willAddAfterNow;

    public WalTimeTypesResponseDto(final Set<WalTimeType> willCancelAfterNow, final Set<WalTimeType> willAddAfterNow) {
        this.willCancelAfterNow = willCancelAfterNow;
        this.willAddAfterNow = willAddAfterNow;
    }

    public Set<WalTimeType> getWillCancelAfterNow() {
        return willCancelAfterNow;
    }

    public Set<WalTimeType> getWillAddAfterNow() {
        return willAddAfterNow;
    }
}
