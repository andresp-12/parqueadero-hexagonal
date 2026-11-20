package usta.parking.application.port.in;

import usta.parking.domain.model.ParkingTicket;

public interface RegisterExitUseCase {
    ParkingTicket execute(Long ticketId);
}
