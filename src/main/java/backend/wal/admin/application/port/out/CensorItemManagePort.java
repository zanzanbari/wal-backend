package backend.wal.admin.application.port.out;

import backend.wal.admin.web.dto.RetrieveCensorItemResponse;
import backend.wal.admin.web.dto.RetrieveCensorItemRequest;
import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;

import java.util.List;

public interface CensorItemManagePort {

    List<RetrieveCensorItemResponse> retrieveUncheckedCensorItemInfo(RetrieveCensorItemRequest request);

    void updateCheckStatus(Long censorItemId, CheckStatus checkStatus);
}
