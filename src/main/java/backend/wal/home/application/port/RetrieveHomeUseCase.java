package backend.wal.home.application.port;

import backend.wal.home.web.dto.HomeResponse;

import java.util.List;

public interface RetrieveHomeUseCase {

    List<HomeResponse> getMainPage(Long userId);
}
