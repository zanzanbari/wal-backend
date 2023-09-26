package backend.wal.wal.censorWal.application.port.in;

import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemRequestDto;
import backend.wal.wal.censorWal.application.port.in.dto.RetrieveCensorItemResponseDto;

import java.util.List;

public interface RetrieveCensorItemUseCase {

    List<RetrieveCensorItemResponseDto> retrieveCensorItemInfo(RetrieveCensorItemRequestDto requestDto);
}
