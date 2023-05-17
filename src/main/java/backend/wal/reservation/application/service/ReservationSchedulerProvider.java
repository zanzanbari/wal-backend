package backend.wal.reservation.application.service;

import backend.wal.reservation.application.port.in.dto.ReservationNotificationRequestDto;
import backend.wal.reservation.domain.aggregate.ScheduledMessage;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.domain.repository.ScheduledMessageRepository;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static backend.wal.reservation.domain.aggregate.SendStatus.NOT_DONE;

@Component
public class ReservationSchedulerProvider {

    private final ReservationNotificationService reservationNotificationService;
    private final ScheduledMessageRepository scheduledMessageRepository;
    private final ReservationRepository reservationRepository;
    private final Clock clock;

    public ReservationSchedulerProvider(final ReservationNotificationService reservationNotificationService,
                                        final ScheduledMessageRepository scheduledMessageRepository,
                                        final ReservationRepository reservationRepository,
                                        final Clock clock) {
        this.reservationNotificationService = reservationNotificationService;
        this.scheduledMessageRepository = scheduledMessageRepository;
        this.reservationRepository = reservationRepository;
        this.clock = clock;
    }

    @PostConstruct
    void reloadReservationSchedules() {
        scheduledMessageRepository
                .findScheduledMessagesBySendDueDateAfter(LocalDateTime.now(clock))
                .stream()
                .map(scheduledMessage -> new ReservationNotificationRequestDto(
                        scheduledMessage.getReservationId(),
                        scheduledMessage.getUserId(),
                        scheduledMessage.getMessage(),
                        scheduledMessage.getSendDueDate(),
                        scheduledMessage.getDelayTimeAboutNow(LocalDateTime.now(clock)))
                )
                .forEach(reservationNotificationService::send);
    }

    @PreDestroy
    void saveReservationSchedules() {
        List<ScheduledMessage> scheduledMessages = reservationRepository
                .findNotDoneReservationAfterNow(LocalDateTime.now(clock), NOT_DONE)
                .stream()
                .map(ScheduledMessage::newInstance)
                .collect(Collectors.toUnmodifiableList());
        scheduledMessageRepository.saveAll(scheduledMessages);
    }
}
