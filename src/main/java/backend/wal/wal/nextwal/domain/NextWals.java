package backend.wal.wal.nextwal.domain;

import java.util.List;

public class NextWals {

    private static final int ZERO = 0;
    private static final int ONE = 1;

    private final List<NextWal> values;
    private int randomIndex;

    public NextWals(final List<NextWal> values) {
        this.values = values;
    }

    public NextWal getRandomBy(RandomRangeGenerator randomRangeGenerator) {
        randomIndex = randomRangeGenerator.generate(values.size());
        return values.get(randomIndex);
    }

    public void updateNextWalInfo(NextWal updatedNextWal) {
        values.set(randomIndex, updatedNextWal);
    }

    public int calculateNextItemId(NextWal nextWal, Long countOfCorrespondCategoryType) {
        double expectNextItemNumberOfCategoryType = nextWal.getCurrentCategoryItemNumber() + ONE;
        double actualNextItemNumberOfCategoryType = expectNextItemNumberOfCategoryType % countOfCorrespondCategoryType;
        if (countOfCorrespondCategoryType == ONE) {
            return (int) expectNextItemNumberOfCategoryType - ONE;
        }
        if (actualNextItemNumberOfCategoryType == ZERO) {
            return (int) expectNextItemNumberOfCategoryType;
        }
        return (int) actualNextItemNumberOfCategoryType;
    }

    public NextWal getCurrentNextWal() {
        return values.get(randomIndex);
    }
}
