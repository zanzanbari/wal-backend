package backend.wal.admin.adapter;

import backend.wal.wal.censorWal.application.port.in.dto.ItemToRegisterDto;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;

import java.util.ArrayList;
import java.util.List;

public class RegisterItemRequestDtoConvertor {

    private final List<ItemToRegisterDto> approvedCensorItemInfo;
    private final double countOfCategoryType;

    public RegisterItemRequestDtoConvertor(List<ItemToRegisterDto> approvedCensorItemInfo,
                                           double countOfCategoryType) {
        this.approvedCensorItemInfo = approvedCensorItemInfo;
        this.countOfCategoryType = countOfCategoryType;
    }

    public List<RegisterItemRequestDto> convert() {
        List<RegisterItemRequestDto> requestDtos = new ArrayList<>();

        double categoryItemNumber = calculateStartCategoryItemNumber();
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

    private double calculateStartCategoryItemNumber() {
        return countOfCategoryType + 1;
    }
}
