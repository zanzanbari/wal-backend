package backend.wal.admin.web.dto;

import backend.wal.wal.common.domain.WalCategoryType;

import javax.validation.constraints.NotNull;

public class RetrieveCensorItemRequest {

    @NotNull
    private WalCategoryType categoryType;

    private RetrieveCensorItemRequest() {
    }

    public RetrieveCensorItemRequest(WalCategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
