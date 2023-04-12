package backend.wal.user.application.port.out;

import backend.wal.user.web.dto.CategoryInfoResponse;
import backend.wal.wal.common.domain.WalCategoryType;

import java.util.Set;

public final class CategoryTypeResponseDto {

    private final Set<WalCategoryType> categoryInfo;

    public CategoryTypeResponseDto(final Set<WalCategoryType> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public CategoryInfoResponse toWebResponse() {
        return new CategoryInfoResponse(categoryInfo);
    }
}
