package backend.wal.wal.censorWal.domain.repository;

import backend.wal.wal.censorWal.domain.aggregate.CensorItem;
import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;
import backend.wal.wal.common.domain.WalCategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CensorItemRepository extends JpaRepository<CensorItem, Long> {

    List<CensorItem> findAllByCategoryTypeAndCheckStatus(WalCategoryType categoryType, CheckStatus checkStatus);

    List<CensorItem> findAllByIdIn(List<Long> censorItemIds);

    @Modifying
    @Query("UPDATE CensorItem c SET c.checkStatus = :checkStatus WHERE c.id = :censorItemId")
    void updateCheckStatus(Long censorItemId, CheckStatus checkStatus);
}
