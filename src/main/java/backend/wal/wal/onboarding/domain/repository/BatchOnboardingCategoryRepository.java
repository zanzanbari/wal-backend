package backend.wal.wal.onboarding.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;

import javax.persistence.EntityManager;
import java.util.List;

@Deprecated(forRemoval = true)
public class BatchOnboardingCategoryRepository {

    private static final String START_BRACKET = "(";
    private static final String END_BRACKET = ")";
    private static final String QUOTES = "'";
    private static final String COMMA = ", ";

    private final EntityManager entityManager;

    public BatchOnboardingCategoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveAllInBatch(List<WalCategoryType> categoryTypes, Long onboardId) {
        String batchSql = "INSERT INTO onboarding_category (category_type, onboarding_id) VALUES "
                + insertValuesBinder(categoryTypes, onboardId);
        entityManager.createNativeQuery(batchSql).executeUpdate();
    }

    private String insertValuesBinder(List<WalCategoryType> categoryTypes, Long onboardId) {
        StringBuilder valuesBuilder = new StringBuilder();
        for (WalCategoryType categoryType : categoryTypes) {
            valuesBuilder.append(START_BRACKET)
                    .append(QUOTES)
                    .append(categoryType)
                    .append(QUOTES)
                    .append(COMMA)
                    .append(onboardId)
                    .append(END_BRACKET)
                    .append(COMMA);
        }
        valuesBuilder.deleteCharAt(valuesBuilder.lastIndexOf(COMMA));
        return valuesBuilder.toString();
    }
}
