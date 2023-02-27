package backend.wal.wal.service;

import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.wal.domain.NextWals;
import backend.wal.wal.domain.RandomRangeGenerator;
import backend.wal.wal.domain.entity.Item;
import backend.wal.wal.domain.entity.NextWal;
import backend.wal.wal.domain.entity.TodayWal;
import backend.wal.wal.repository.ItemRepository;
import backend.wal.wal.repository.NextWalRepository;
import backend.wal.wal.repository.TodayWalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalSettingService {

    private final ItemRepository itemRepository;
    private final NextWalRepository nextWalRepository;
    private final TodayWalRepository todayWalRepository;
    private final RandomRangeGenerator randomRangeGenerator;

    public NextWals setNextWals(Set<WalCategoryType> categoryTypes, Long userId) {
        List<NextWal> nextWals = categoryTypes.stream()
                .map(categoryType -> {
                    Item firstItem = itemRepository.findFirstByCategoryCategoryType(categoryType);
                    NextWal nextWal = NextWal.newInstance(userId, categoryType, firstItem);
                    return nextWalRepository.save(nextWal);
                })
                .collect(Collectors.toList());
        return new NextWals(nextWals);
    }

    public void setTodayWals(Set<WalTimeType> timeTypes, Long userId, NextWals nextWals) {
        for (WalTimeType timeType : timeTypes) {
            NextWal randomNextWal = nextWals.getRandomBy(randomRangeGenerator);
            WalCategoryType categoryType = randomNextWal.getCategoryType();
            TodayWal todayWal = TodayWal.builder()
                    .userId(userId)
                    .timeType(timeType)
                    .categoryType(categoryType)
                    .message(randomNextWal.getItemContent())
                    .build();
            todayWalRepository.save(todayWal);
            updateNextWal(nextWals, randomNextWal, categoryType);
        }
    }

    private void updateNextWal(NextWals nextWals, NextWal randomNextWal, WalCategoryType categoryType) {
        Long countOfCorrespondCategoryType = itemRepository.countAllByCategoryCategoryType(categoryType);
        double nextItemId = nextWals.calculateNextItemId(randomNextWal, countOfCorrespondCategoryType);
        Item nextItem = itemRepository.findByCategoryCategoryTypeAndCategoryItemNumber(categoryType, nextItemId);
        randomNextWal.updateItemToNextItem(nextItem);
        nextWals.updateNextWalInfo(randomNextWal);
    }
}
