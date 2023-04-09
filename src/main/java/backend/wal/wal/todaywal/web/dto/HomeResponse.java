package backend.wal.wal.todaywal.web.dto;

import backend.wal.wal.todaywal.application.port.in.HomeResponseDto;

import java.util.List;

public class HomeResponse {

    private List<HomeResponseDto> todayWalInfo;

    private HomeResponse() {
    }

    public HomeResponse(final List<HomeResponseDto> todayWalInfo) {
        this.todayWalInfo = todayWalInfo;
    }

    public List<HomeResponseDto> getTodayWalInfo() {
        return todayWalInfo;
    }
}
