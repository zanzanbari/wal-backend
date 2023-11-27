package backend.wal.wal.nextwal.adapter.out.persistence;

import backend.wal.wal.common.domain.WalCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface NextWalRepository extends JpaRepository<NextWalEntity, Long>, NextWalRepositoryCustom {

    List<NextWalEntity> findNextWalsByCategoryTypeInAndUserId(Collection<WalCategoryType> categoryType, Long userId);

    @Modifying
    @Query("UPDATE NextWalEntity nw SET nw.itemId = :nextItemId WHERE nw.id = :nextWalId")
    void updateNextWalItem(Long nextItemId, Long nextWalId);

    @Query("SELECT new backend.wal.wal.nextwal.adapter.out.persistence.NextWalWithItem(nw, i) " +
            "FROM NextWalEntity nw " +
            "LEFT JOIN ItemEntity i ON nw.itemId = i.id " +
            "WHERE nw.userId = :userId")
    List<NextWalWithItem> findNextWalsByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
