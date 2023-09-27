package backend.wal.admin.adapter;

import backend.wal.wal.censorWal.application.port.in.dto.ApprovedCensorItemResponseDto;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;

import java.util.ArrayList;
import java.util.List;

public class RegisterItemRequestDtoConvertor {

    private final List<ApprovedCensorItemResponseDto> approvedCensorItemInfo;
    private final double countOfCategoryType;

    public RegisterItemRequestDtoConvertor(List<ApprovedCensorItemResponseDto> approvedCensorItemInfo,
                                           double countOfCategoryType) {
        this.approvedCensorItemInfo = approvedCensorItemInfo;
        this.countOfCategoryType = countOfCategoryType;
    }

    public List<RegisterItemRequestDto> convert() {
        List<RegisterItemRequestDto> requestDtos = new ArrayList<>();

        double categoryItemNumber = calculateStartCategoryItemNumber();
        for (ApprovedCensorItemResponseDto responseDto : approvedCensorItemInfo) {
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
