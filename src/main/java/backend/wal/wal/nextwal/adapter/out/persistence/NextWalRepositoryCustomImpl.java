package backend.wal.wal.nextwal.adapter.out.persistence;

import javax.persistence.EntityManager;
import java.util.List;

public class NextWalRepositoryCustomImpl implements NextWalRepositoryCustom {

    private static final String START_BRACKET = "(";
    private static final String END_BRACKET = ")";
    private static final String QUOTES = "'";
    private static final String COMMA = ", ";

    private final EntityManager entityManager;

    public NextWalRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveAllInBatch(List<NextWalEntity> nextWalEntities) {
        String batchInsert = "INSERT INTO next_wal (category_type, item_id, user_id) VALUES "
                + createValues(nextWalEntities);
        entityManager.createNativeQuery(batchInsert).executeUpdate();
    }
    
    private String createValues(List<NextWalEntity> nextWalEntities) {
        StringBuilder valuesBuilder = new StringBuilder();
        for (NextWalEntity nextWalEntity : nextWalEntities) {
            valuesBuilder.append(START_BRACKET)
                    .append(QUOTES)
                    .append(nextWalEntity.getCategoryType())
                    .append(QUOTES)
                    .append(COMMA)
                    .append(nextWalEntity.getItemId())
                    .append(COMMA)
                    .append(nextWalEntity.getUserId())
                    .append(END_BRACKET)
                    .append(COMMA);
        }
        valuesBuilder.deleteCharAt(valuesBuilder.lastIndexOf(COMMA));
        return valuesBuilder.toString();
    }
}
