package usta.parking.application.port.in;

import usta.parking.domain.model.*;
import java.util.List;

public interface QueryParkingUseCase {
    List<ParkingSpot> listSpots();
    List<ParkingTicket> listTickets();
    List<ParkingTicket> listActiveTickets();
    ParkingTicket findTicket(Long id);
    ParkingSummary summary();
}
