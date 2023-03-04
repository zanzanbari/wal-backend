package backend.wal.wal.service;

import backend.wal.onboarding.domain.WalTimeTypes;
import backend.wal.onboarding.domain.entity.WalCategoryType;
import backend.wal.onboarding.domain.entity.WalTimeType;
import backend.wal.wal.domain.NextWals;
import backend.wal.wal.domain.RandomRangeGenerator;
import backend.wal.wal.domain.entity.Item;
import backend.wal.wal.domain.entity.NextWal;
import backend.wal.wal.domain.entity.TodayWal;
import backend.wal.wal.domain.repository.ItemRepository;
import backend.wal.wal.domain.repository.NextWalRepository;
import backend.wal.wal.domain.repository.TodayWalRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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

    public void updateTodayWalByCancelTimeTypes(WalTimeTypes willCancelAfterNow, Long userId) {
        deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
    }

    public void updateTodayWalByAddTimeTypes(WalTimeTypes willAddAfterNow, Long userId) {
        NextWals nextWals = new NextWals(nextWalRepository.findNextWalsByUserId(userId));
        setTodayWals(willAddAfterNow.getValues(), userId, nextWals);
    }

    public Set<WalTimeType> updateWalInfoByCancelCategoryTypes(Set<WalCategoryType> canceledCategoryTypes,
                                                               Long userId) {
        deleteNextWalsByCanceledCategoryTypes(canceledCategoryTypes, userId);
        WalTimeTypes willCancelAfterNow = extractAfterNow(canceledCategoryTypes, userId);
        deleteTodayWalsByWillCancelAfterNow(willCancelAfterNow, userId);
        return willCancelAfterNow.getValues();
    }

    private void deleteNextWalsByCanceledCategoryTypes(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        nextWalRepository.deleteAllInBatch(
                nextWalRepository.findNextWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }

    @NotNull
    private WalTimeTypes extractAfterNow(Set<WalCategoryType> canceledCategoryTypes, Long userId) {
        return WalTimeTypes.createCompareAfterNow(
                todayWalRepository.findTodayWalsByCategoryTypeInAndUserId(canceledCategoryTypes, userId));
    }

    private void deleteTodayWalsByWillCancelAfterNow(WalTimeTypes willCancelAfterNow, Long userId) {
        todayWalRepository.deleteAllInBatch(
                todayWalRepository.findTodayWalsByTimeTypeInAndUserId(willCancelAfterNow.getValues(), userId));
        if (willCancelAfterNow.isExist()) {
            todayWalRepository.deleteAllInBatch(
                    todayWalRepository.findTodayWalsByTimeTypeInAndUserId(willCancelAfterNow.getValues(), userId));
        }
    }

    public void updateWalInfoByAddCategoryTypesInEmptyTimeTypes(Set<WalTimeType> emptiedTimeTypes,
                                                                Set<WalCategoryType> addedCategoryTypes,
                                                                Long userId) {
        NextWals nextWals = setNextWals(addedCategoryTypes, userId);
        setTodayWals(emptiedTimeTypes, userId, nextWals);
    }
}
