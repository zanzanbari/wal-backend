package backend.wal.wal.onboarding.domain.repository;

import backend.wal.wal.common.domain.WalTimeType;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Deprecated(forRemoval = true)
public class BatchOnboardingTimeRepository implements JdbcOnboardingTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public BatchOnboardingTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAllInBatch(List<WalTimeType> timeTypes, Long onboardId) {
        String batchSql = "INSERT INTO onboarding_time (time_type, onboarding_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(batchSql, getBatchPreparedStatementSetter(timeTypes, onboardId));
    }

    @NotNull
    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(
            List<WalTimeType> timeTypes,
            Long onboardId) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WalTimeType timeType = timeTypes.get(i);
                ps.setString(1, timeType.name());
                ps.setLong(2, onboardId);
            }

            @Override
            public int getBatchSize() {
                return timeTypes.size();
            }
        };
    }
}
