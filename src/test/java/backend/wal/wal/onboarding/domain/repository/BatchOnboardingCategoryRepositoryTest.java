package backend.wal.wal.onboarding.domain.repository;

import backend.wal.config.JpaRepositoryTestConfig;
import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.common.domain.WalTimeType;
import backend.wal.wal.onboarding.domain.aggregate.Onboarding;
import backend.wal.wal.onboarding.domain.aggregate.OnboardingCategory;
import backend.wal.wal.onboarding.domain.aggregate.OnboardingTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Set;

import static backend.wal.wal.common.domain.WalCategoryType.*;
import static backend.wal.wal.common.domain.WalTimeType.*;
import static org.assertj.core.api.Assertions.assertThat;

@JpaRepositoryTestConfig
class BatchOnboardingCategoryRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BatchOnboardingCategoryRepository batchOnboardingCategoryRepository;


    @DisplayName("CascadeType 이 PERSIST 가 아니면 부모 객체에 대한 자식 객체는 영속성 컨텍스트에서 관리되지 않는다")
    @Test
    void test() {
        // given
        Set<WalCategoryType> categoryTypes = Set.of(COMEDY, COMFORT, FUSS, YELL);
        Set<WalTimeType> timeTypes = Set.of(MORNING, AFTERNOON, NIGHT);

        Onboarding onboarding = Onboarding.newInstance(1L);
        onboarding.addCategories(categoryTypes); // CascadeType.MERGE, CascadeType.REMOVE : 영속성 컨텍스트에서 관리 안함
        onboarding.addTimes(timeTypes); // CascadeType.PERSIST : 영속성 컨텍스트에서 관리함

        // when
        entityManager.persist(onboarding);
        batchOnboardingCategoryRepository.saveAllInBatch( // 여기서 flush 해버림
                new ArrayList<>(onboarding.getCategoryTypes()),
                onboarding.getId()
        );
        Onboarding save = entityManager.find(Onboarding.class, onboarding.getId());
//        entityManager.merge(save); // merge 하면 category 를 영속성 컨텍스트에서 관리

        // then
        for (OnboardingCategory category : save.getCategories()) {
            assertThat(entityManager.contains(category)).isFalse();
        }
        for (OnboardingTime time : save.getTimes()) {
            assertThat(entityManager.contains(time)).isTrue();
        }
    }
}