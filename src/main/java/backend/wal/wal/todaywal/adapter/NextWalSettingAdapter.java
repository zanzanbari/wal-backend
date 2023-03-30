package backend.wal.wal.todaywal.adapter;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.nextwal.application.port.GetNextWalUseCase;
import backend.wal.wal.nextwal.application.port.NextWalSettingUseCase;
import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.todaywal.application.port.out.NextWalSettingPort;
import org.springframework.stereotype.Component;

@Component
public final class NextWalSettingAdapter implements NextWalSettingPort {

    private final NextWalSettingUseCase nextWalSettingUseCase;
    private final GetNextWalUseCase getNextWalUseCase;

    public NextWalSettingAdapter(final NextWalSettingUseCase nextWalSettingUseCase,
                                 final GetNextWalUseCase getNextWalUseCase) {
        this.nextWalSettingUseCase = nextWalSettingUseCase;
        this.getNextWalUseCase = getNextWalUseCase;
    }

    @Override
    public NextWal getRandomNextWal(NextWals nextWals) {
        return getNextWalUseCase.getRandomNextWal(nextWals);
    }

    @Override
    public void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        nextWalSettingUseCase.updateNextWal(nextWals, randomNextWal, categoryType);
    }
}
