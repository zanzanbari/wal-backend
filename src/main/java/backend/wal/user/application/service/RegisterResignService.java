package backend.wal.user.application.service;

import backend.wal.user.application.port.in.RegisterResignRequestDto;
import backend.wal.user.application.port.in.RegisterResignInfoUseCase;
import backend.wal.user.domain.repository.ResignRepository;
import backend.wal.user.domain.aggregate.entity.Resign;
import backend.wal.support.annotation.AppService;

import org.springframework.transaction.annotation.Transactional;

@AppService
public class RegisterResignService implements RegisterResignInfoUseCase {

    private final ResignRepository resignRepository;

    public RegisterResignService(final ResignRepository resignRepository) {
        this.resignRepository = resignRepository;
    }

    @Override
    @Transactional
    public void register(RegisterResignRequestDto requestDto) {
        resignRepository.save(Resign.newInstance(requestDto.getUserId(), requestDto.getReasons()));
    }
}
