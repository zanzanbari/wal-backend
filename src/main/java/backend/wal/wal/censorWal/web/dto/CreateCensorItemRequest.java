package backend.wal.wal.censorWal.web.dto;

import backend.wal.wal.censorWal.application.port.in.dto.CreateCensorItemRequestDto;
import backend.wal.wal.common.domain.WalCategoryType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCensorItemRequest {

    @NotNull
    private WalCategoryType categoryType;

    @NotBlank
    private String contents;

    public CreateCensorItemRequest(WalCategoryType categoryType, String contents) {
        this.categoryType = categoryType;
        this.contents = contents;
    }

    public CreateCensorItemRequestDto toServiceDto() {
        return new CreateCensorItemRequestDto(categoryType, contents);
    }
}
