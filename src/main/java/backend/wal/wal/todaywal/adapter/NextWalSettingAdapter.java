package backend.wal.wal.todaywal.adapter;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.nextwal.application.port.in.RetrieveNextWalUseCase;
import backend.wal.wal.nextwal.application.port.in.NextWalSettingUseCase;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.NextWal;
import backend.wal.wal.todaywal.application.port.out.NextWalSettingPort;

import org.springframework.stereotype.Component;

@Component
public final class NextWalSettingAdapter implements NextWalSettingPort {

    private final NextWalSettingUseCase nextWalSettingUseCase;
    private final RetrieveNextWalUseCase retrieveNextWalUseCase;

    public NextWalSettingAdapter(final NextWalSettingUseCase nextWalSettingUseCase,
                                 final RetrieveNextWalUseCase retrieveNextWalUseCase) {
        this.nextWalSettingUseCase = nextWalSettingUseCase;
        this.retrieveNextWalUseCase = retrieveNextWalUseCase;
    }

    @Override
    public NextWal getRandomNextWal(NextWals nextWals) {
        return retrieveNextWalUseCase.getRandomNextWal(nextWals);
    }

    @Override
    public void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        nextWalSettingUseCase.updateNextWal(nextWals, randomNextWal, categoryType);
    }
}
