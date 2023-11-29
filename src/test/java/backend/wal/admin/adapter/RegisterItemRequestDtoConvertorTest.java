package backend.wal.admin.adapter;

import backend.wal.wal.censorWal.application.port.in.dto.ItemToRegisterDto;
import backend.wal.wal.item.application.port.in.RegisterItemRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import static backend.wal.wal.common.domain.WalCategoryType.COMEDY;

class RegisterItemRequestDtoConvertorTest {

    private static final int COUNT_OF_CATEGORY_TYPE = 100;

    @DisplayName("승인 처리된 검열 아이템 정보들을 아이템 등록을 위한 정보들로 변환시킨다")
    @Test
    void convert() {
        // given
        List<ItemToRegisterDto> approvedCensorItemsInfo = List.of(
                new ItemToRegisterDto(COMEDY, "드립1", ""),
                new ItemToRegisterDto(COMEDY, "드립2", ""),
                new ItemToRegisterDto(COMEDY, "드립3", "")
        );
        RegisterItemRequestDtoConvertor convertor =
                new RegisterItemRequestDtoConvertor(approvedCensorItemsInfo, COUNT_OF_CATEGORY_TYPE);
        int start = (int) (COUNT_OF_CATEGORY_TYPE + 1);
        int end = (int) (COUNT_OF_CATEGORY_TYPE + approvedCensorItemsInfo.size());

        // when
        List<RegisterItemRequestDto> registerItemRequestInfo = convertor.convert();

        // then
        List<Integer> expectNumbers = IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
        List<Integer> actualNumbers = registerItemRequestInfo.stream()
                .map(RegisterItemRequestDto::getCategoryItemNumber)
                .collect(Collectors.toUnmodifiableList());

        List<String> expectContents = approvedCensorItemsInfo.stream()
                .map(ItemToRegisterDto::getContents)
                .collect(Collectors.toUnmodifiableList());
        List<String> actualContents = registerItemRequestInfo.stream()
                .map(RegisterItemRequestDto::getContents)
                .collect(Collectors.toUnmodifiableList());

        assertAll(
                () -> assertThat(actualNumbers).isEqualTo(expectNumbers),
                () -> assertThat(actualContents).isEqualTo(expectContents)
        );
    }
}