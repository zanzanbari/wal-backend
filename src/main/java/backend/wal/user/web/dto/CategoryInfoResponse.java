package backend.wal.user.web.dto;

import backend.wal.wal.common.domain.WalCategoryType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryInfoResponse {

    private Set<WalCategoryType> categoryInfo;

    public CategoryInfoResponse(final Set<WalCategoryType> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }
}
