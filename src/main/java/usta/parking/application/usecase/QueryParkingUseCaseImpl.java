package usta.parking.application.usecase;

import usta.parking.application.exception.TicketNotFoundException;
import usta.parking.application.port.in.QueryParkingUseCase;
import usta.parking.application.port.out.*;
import usta.parking.domain.model.*;
import java.time.LocalDate;
import java.util.List;

public class QueryParkingUseCaseImpl implements QueryParkingUseCase {
    private final SpotRepositoryPort spots;
    private final TicketRepositoryPort tickets;

    public QueryParkingUseCaseImpl(SpotRepositoryPort spots, TicketRepositoryPort tickets) {
        this.spots = spots; this.tickets = tickets;
    }

    public List<ParkingSpot> listSpots() { return spots.findAll(); }
    public List<ParkingTicket> listTickets() { return tickets.findAll(); }
    public List<ParkingTicket> listActiveTickets() {
        return tickets.findAll().stream().filter(t -> t.status() == ParkingTicket.Status.ACTIVE).toList();
    }
    public ParkingTicket findTicket(Long id) { return tickets.findById(id).orElseThrow(TicketNotFoundException::new); }

    public ParkingSummary summary() {
        List<ParkingSpot> all = spots.findAll();
        List<ParkingTicket> ts = tickets.findAll();
        LocalDate today = LocalDate.now();
        long revenue = ts.stream().filter(t -> t.status() == ParkingTicket.Status.CLOSED
                && t.exitTime().toLocalDate().equals(today)).mapToLong(ParkingTicket::amount).sum();
        return new ParkingSummary(
                all.stream().filter(s -> s.type() == VehicleType.CAR && !s.occupied()).count(),
                all.stream().filter(s -> s.type() == VehicleType.MOTORCYCLE && !s.occupied()).count(),
                ts.stream().filter(t -> t.status() == ParkingTicket.Status.ACTIVE).count(), revenue);
    }
}
