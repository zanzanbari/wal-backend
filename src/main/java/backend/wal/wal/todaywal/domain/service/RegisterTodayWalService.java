package backend.wal.wal.todaywal.domain.service;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.todaywal.domain.aggregate.TodayWal;
import backend.wal.wal.todaywal.domain.repository.TodayWalRepository;
import backend.wal.support.annotation.DomainService;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DomainService
public class RegisterTodayWalService {

    private final TodayWalRepository todayWalRepository;
    private final Clock clock;

    public RegisterTodayWalService(final TodayWalRepository todayWalRepository, final Clock clock) {
        this.todayWalRepository = todayWalRepository;
        this.clock = clock;
    }

    public void register(Long userId, WalTimeType timeType, WalCategoryType categoryType, String content) {
        LocalDateTime sendTime = LocalDateTime.of(LocalDate.now(clock), timeType.getSendTime());
        TodayWal todayWal = TodayWal.builder()
                .userId(userId)
                .timeType(timeType)
                .categoryType(categoryType)
                .message(content)
                .sendTime(sendTime)
                .build();
        todayWalRepository.save(todayWal);
    }
}
