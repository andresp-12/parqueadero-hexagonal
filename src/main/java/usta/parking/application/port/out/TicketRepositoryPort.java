package usta.parking.application.port.out;

import usta.parking.domain.model.ParkingTicket;
import java.util.*;

public interface TicketRepositoryPort {
    List<ParkingTicket> findAll();
    Optional<ParkingTicket> findById(Long id);
    ParkingTicket save(ParkingTicket ticket);
}
