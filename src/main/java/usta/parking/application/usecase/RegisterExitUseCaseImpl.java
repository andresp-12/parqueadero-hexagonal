package usta.parking.application.usecase;

import usta.parking.application.exception.TicketNotFoundException;
import usta.parking.application.port.in.RegisterExitUseCase;
import usta.parking.application.port.out.*;
import usta.parking.domain.exception.BusinessException;
import usta.parking.domain.model.*;
import usta.parking.domain.service.FeeCalculator;
import java.time.LocalDateTime;

public class RegisterExitUseCaseImpl implements RegisterExitUseCase {
    private final SpotRepositoryPort spots;
    private final TicketRepositoryPort tickets;
    private final FeeCalculator calculator;

    public RegisterExitUseCaseImpl(SpotRepositoryPort spots, TicketRepositoryPort tickets, FeeCalculator calculator) {
        this.spots = spots; this.tickets = tickets; this.calculator = calculator;
    }

    public ParkingTicket execute(Long ticketId) {
        ParkingTicket ticket = tickets.findById(ticketId).orElseThrow(TicketNotFoundException::new);
        if (ticket.status() == ParkingTicket.Status.CLOSED) throw new BusinessException("Este ticket ya fue cobrado");
        spots.save(spots.findById(ticket.spot().id()).orElseThrow().release());
        LocalDateTime now = LocalDateTime.now();
        long fee = calculator.calculate(ticket.spot().type(), ticket.entryTime(), now);
        return tickets.save(ticket.close(now, fee));
    }
}
