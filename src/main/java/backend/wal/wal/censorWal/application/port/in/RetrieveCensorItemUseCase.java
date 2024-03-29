package backend.wal.wal.censorWal.application.port.in;

import backend.wal.wal.censorWal.application.port.in.dto.ItemToRegisterDto;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.censorWal.application.port.in.dto.UncheckedCensorItemResponseDto;

import java.util.List;

public interface RetrieveCensorItemUseCase {

    List<UncheckedCensorItemResponseDto> retrieveUncheckedCensorItemInfo(RetrieveCensorItemRequestDto requestDto);

    List<ItemToRegisterDto> retrieveApprovedCensorItemInfo(RetrieveCensorItemRequestDto requestDto);
}
