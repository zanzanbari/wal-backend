package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.NextWal;
import backend.wal.wal.todaywal.application.port.out.NextWalSettingPort;
import backend.wal.wal.todaywal.application.port.in.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.service.RegisterTodayWalService;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AppService
public class TodayWalSettingService implements TodayWalSettingUseCase {

    private final RegisterTodayWalService registerTodayWalService;
    private final NextWalSettingPort nextWalSettingPort;

    public TodayWalSettingService(final RegisterTodayWalService registerTodayWalService,
                                  final NextWalSettingPort nextWalSettingPort) {
        this.registerTodayWalService = registerTodayWalService;
        this.nextWalSettingPort = nextWalSettingPort;
    }

    @Override
    @Transactional
    public void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals) {
        for (WalTimeType timeType : timeTypes) {
            NextWal randomNextWal = nextWalSettingPort.getRandomNextWal(nextWals);
            WalCategoryType categoryType = randomNextWal.getCategoryType();
            registerTodayWalService.register(userId, timeType, categoryType, randomNextWal.getItemContents());
            nextWalSettingPort.updateNextWal(nextWals, randomNextWal, categoryType);
        }
    }
}
