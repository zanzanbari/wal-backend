package backend.wal.wal.todaywal.domain.view;

import backend.wal.wal.todaywal.application.port.in.HomeResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class Homes {

    private final List<Home> values;

    private Homes(final List<Home> values) {
        this.values = values;
    }

    public static Homes create(final List<Home> values, final LocalDateTime now) {
        values.forEach(home -> home.setOpenStatusBy(now));
        return new Homes(values);
    }

    public List<HomeResponseDto> getServiceResponseDto() {
        return values.stream()
                .map(HomeResponseDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<OpenStatus> getEachOpenStatus() {
        return values.stream()
                .map(Home::getOpenStatus)
                .collect(Collectors.toUnmodifiableList());
    }
}
