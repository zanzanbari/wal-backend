package backend.wal.reservation.application.service;

import static backend.wal.reservation.domain.aggregate.SendStatus.*;
import static backend.wal.reservation.domain.aggregate.ShowStatus.*;

import backend.wal.reservation.application.port.in.dto.ReservationNotificationRequestDto;
import backend.wal.reservation.domain.aggregate.Reservation;
import backend.wal.reservation.domain.aggregate.ScheduledMessage;
import backend.wal.reservation.domain.aggregate.SendStatus;
import backend.wal.reservation.domain.repository.ReservationRepository;
import backend.wal.reservation.domain.repository.ScheduledMessageRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationSchedulerManagerTest {

    @Mock
    private ReservationNotificationService reservationNotificationService;

    @Mock
    private ScheduledMessageRepository scheduledMessageRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private Clock clock;

    @InjectMocks
    private ReservationSchedulerManager reservationSchedulerManager;

    @DisplayName("스케줄된 메시지 정보를 가져와 예약 스케줄링에 재등록한다")
    @Test
    void reloadReservationSchedules() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 12, 25, 12, 0);
        when(clock.instant()).thenReturn(now.toInstant(ZoneOffset.UTC));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        ScheduledMessage scheduledMessage1 = new ScheduledMessage(1L, 1L, "Hello", now.plusHours(1));
        ScheduledMessage scheduledMessage2 = new ScheduledMessage(2L, 2L, "Hi", now.plusHours(2));
        List<ScheduledMessage> scheduledMessages = List.of(scheduledMessage1, scheduledMessage2);

        when(scheduledMessageRepository.findScheduledMessagesBySendDueDateAfter(any(LocalDateTime.class)))
                .thenReturn(scheduledMessages);

        // when
        reservationSchedulerManager.reloadReservationSchedules();

        // then
        ReservationNotificationRequestDto taskRequest1 = new ReservationNotificationRequestDto(
                scheduledMessage1.getReservationId(),
                scheduledMessage1.getUserId(),
                scheduledMessage1.getMessage(),
                scheduledMessage1.getSendDueDate(),
                scheduledMessage1.getDelayTimeAboutNow(now.plusMinutes(10))
        );
        ReservationNotificationRequestDto taskRequest2 = new ReservationNotificationRequestDto(
                scheduledMessage2.getReservationId(),
                scheduledMessage2.getUserId(),
                scheduledMessage2.getMessage(),
                scheduledMessage2.getSendDueDate(),
                scheduledMessage2.getDelayTimeAboutNow(now.plusMinutes(10))
        );
        verify(reservationNotificationService).send(taskRequest1);
        verify(reservationNotificationService).send(taskRequest2);
    }

    @DisplayName("예약 메시지를 찾아와 스케줄된 메시지 정보를 데이터베이스에 저장한다")
    @Test
    @SuppressWarnings("unchecked")
    void saveReservationSchedules() {
        // given
        LocalDateTime now = LocalDateTime.of(2021, 12, 25, 12, 0);
        when(clock.instant()).thenReturn(now.toInstant(ZoneOffset.UTC));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Reservation reservation1 = new Reservation(1L, "Hello", now.plusHours(1), OPEN, NOT_DONE);
        Reservation reservation2 = new Reservation(2L, "Hi", now.plusHours(2), OPEN, NOT_DONE);
        List<Reservation> reservations = List.of(reservation1, reservation2);

        when(reservationRepository.findNotDoneReservationAfterNow(any(LocalDateTime.class), any(SendStatus.class)))
                .thenReturn(reservations);

        // when
        reservationSchedulerManager.saveReservationSchedules();

        // then
        ArgumentCaptor<List<ScheduledMessage>> captor = ArgumentCaptor.forClass(List.class);
        verify(scheduledMessageRepository).saveAll(captor.capture());
        List<ScheduledMessage> savedScheduledMessages = captor.getValue();
        verify(scheduledMessageRepository).saveAll(savedScheduledMessages);
    }
}