package backend.wal.admin.adapter;

import backend.wal.wal.censorWal.application.port.in.dto.ItemToRegisterDto;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;

import java.util.ArrayList;
import java.util.List;

public class RegisterItemRequestDtoConvertor {

    private final List<ItemToRegisterDto> approvedCensorItemInfo;
    private final int countOfCategoryType;

    public RegisterItemRequestDtoConvertor(List<ItemToRegisterDto> approvedCensorItemInfo,
                                           int countOfCategoryType) {
        this.approvedCensorItemInfo = approvedCensorItemInfo;
        this.countOfCategoryType = countOfCategoryType;
    }

    public List<RegisterItemRequestDto> convert() {
        List<RegisterItemRequestDto> requestDtos = new ArrayList<>();

        int categoryItemNumber = calculateStartCategoryItemNumber();
        for (ItemToRegisterDto responseDto : approvedCensorItemInfo) {
            RegisterItemRequestDto requestDto = new RegisterItemRequestDto(
                    responseDto.getContents(),
                    responseDto.getImageUrl(),
                    categoryItemNumber
            );
            requestDtos.add(requestDto);
            categoryItemNumber += 1;
        }

        return requestDtos;
    }

    private int calculateStartCategoryItemNumber() {
        return countOfCategoryType + 1;
    }
}
