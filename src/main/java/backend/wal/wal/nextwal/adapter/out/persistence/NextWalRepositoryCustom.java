package backend.wal.wal.nextwal.adapter.out.persistence;

import java.util.List;

public interface NextWalRepositoryCustom {

    void saveAllInBatch(List<NextWalEntity> nextWalEntities);
}
