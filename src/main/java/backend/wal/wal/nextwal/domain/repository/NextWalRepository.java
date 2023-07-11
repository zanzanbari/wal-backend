package backend.wal.wal.nextwal.domain.repository;

import backend.wal.wal.common.domain.WalCategoryType;
import backend.wal.wal.nextwal.domain.aggregate.NextWal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface NextWalRepository extends JpaRepository<NextWal, Long> {

    List<NextWal> findNextWalsByUserId(Long userId);

    List<NextWal> findNextWalsByCategoryTypeInAndUserId(Set<WalCategoryType> categoryTypes, Long userId);

    @Query("SELECT nw FROM NextWal nw JOIN FETCH nw.item WHERE nw.userId = :userId")
    List<NextWal> findNextWalsWithItemByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
