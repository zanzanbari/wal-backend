package backend.wal.config.jpa;

import backend.wal.wal.onboarding.domain.repository.BatchOnboardingCategoryRepository;
import backend.wal.wal.onboarding.domain.repository.BatchOnboardingTimeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;

@Configuration
@EnableJpaAuditing
public class JPAConfig {

    @Bean
    @Deprecated(forRemoval = true)
    public BatchOnboardingTimeRepository batchOnboardingTimeRepository(JdbcTemplate jdbcTemplate) {
        return new BatchOnboardingTimeRepository(jdbcTemplate);
    }

    @Bean
    @Deprecated(forRemoval = true)
    public BatchOnboardingCategoryRepository batchOnboardingCategoryRepository(EntityManager entityManager) {
        return new BatchOnboardingCategoryRepository(entityManager);
    }
}
