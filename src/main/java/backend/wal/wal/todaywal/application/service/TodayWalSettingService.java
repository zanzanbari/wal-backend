package backend.wal.wal.todaywal.application.service;

import backend.wal.wal.nextwal.domain.NextWals;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;
import backend.wal.wal.todaywal.application.port.out.NextWalSettingPort;
import backend.wal.wal.todaywal.application.port.in.TodayWalSettingUseCase;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AppService
public class TodayWalSettingService implements TodayWalSettingUseCase {

    private final TodayWalRepository todayWalRepository;
    private final NextWalSettingPort nextWalSettingPort;

    public TodayWalSettingService(final TodayWalRepository todayWalRepository,
                                  final NextWalSettingPort nextWalSettingPort) {
        this.todayWalRepository = todayWalRepository;
        this.nextWalSettingPort = nextWalSettingPort;
    }

    @Override
    @Transactional
    public void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals) {
        for (WalTimeType timeType : timeTypes) {
            NextWal randomNextWal = nextWalSettingPort.getRandomNextWal(nextWals);
            WalCategoryType categoryType = randomNextWal.getCategoryType();
            registerTodayWal(userId, timeType, categoryType, randomNextWal.getItemContent());
            nextWalSettingPort.updateNextWal(nextWals, randomNextWal, categoryType);
        }
    }

    private void registerTodayWal(Long userId, WalTimeType timeType, WalCategoryType categoryType, String content) {
        TodayWal todayWal = TodayWal.builder()
                .userId(userId)
                .timeType(timeType)
                .categoryType(categoryType)
                .message(content)
                .build();
        todayWalRepository.save(todayWal);
    }
}
