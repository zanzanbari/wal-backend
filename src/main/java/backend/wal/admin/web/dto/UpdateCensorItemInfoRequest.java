package backend.wal.admin.web.dto;

import backend.wal.wal.censorWal.domain.aggregate.CheckStatus;

import javax.validation.constraints.NotNull;

public class UpdateCensorItemInfoRequest {

    @NotNull
    private Long censorItemId;

    @NotNull
    private CheckStatus checkStatus;

    public UpdateCensorItemInfoRequest(Long censorItemId, CheckStatus checkStatus) {
        this.censorItemId = censorItemId;
        this.checkStatus = checkStatus;
    }

    public Long getCensorItemId() {
        return censorItemId;
    }

    public CheckStatus getCheckStatus() {
        return checkStatus;
    }
}
