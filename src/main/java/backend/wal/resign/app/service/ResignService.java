package backend.wal.resign.app.service;

import backend.wal.resign.app.dto.RegisterResignRequestDto;
import backend.wal.resign.domain.Resign;
import backend.wal.resign.domain.repository.ResignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResignService {

    private final ResignRepository resignRepository;

    @Transactional
    public void registerResignInfo(RegisterResignRequestDto requestDto) {
        resignRepository.save(Resign.newInstance(requestDto.getUserId(), requestDto.getReasons()));
    }
}
