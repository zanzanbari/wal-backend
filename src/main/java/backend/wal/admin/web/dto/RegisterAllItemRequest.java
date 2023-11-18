package backend.wal.admin.web.dto;

import backend.wal.wal.common.domain.WalCategoryType;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class RegisterAllItemRequest {

    @NotNull
    private Set<String> contents;

    @NotNull
    private WalCategoryType categoryType;

    public RegisterAllItemRequest(Set<String> contents, WalCategoryType categoryType) {
        this.contents = contents;
        this.categoryType = categoryType;
    }

    public Set<String> getContents() {
        return contents;
    }

    public WalCategoryType getCategoryType() {
        return categoryType;
    }
}
