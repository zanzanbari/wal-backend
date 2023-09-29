package backend.wal.wal.censorWal.application.port.in;

import backend.wal.wal.censorWal.application.port.in.dto.CreateCensorItemRequestDto;

public interface CreateCensorItemUseCase {
    void create(CreateCensorItemRequestDto requestDto);
}
