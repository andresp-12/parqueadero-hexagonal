package usta.parking.application.usecase;

import usta.parking.application.exception.NoAvailableSpotException;
import usta.parking.application.port.in.RegisterEntryUseCase;
import usta.parking.application.port.out.*;
import usta.parking.domain.exception.BusinessException;
import usta.parking.domain.model.*;
import java.time.LocalDateTime;

public class RegisterEntryUseCaseImpl implements RegisterEntryUseCase {
    private final SpotRepositoryPort spots;
    private final TicketRepositoryPort tickets;

    public RegisterEntryUseCaseImpl(SpotRepositoryPort spots, TicketRepositoryPort tickets) {
        this.spots = spots; this.tickets = tickets;
    }

    public ParkingTicket execute(String plate, VehicleType type) {
        String p = plate == null ? "" : plate.trim().toUpperCase();
        if (!p.matches("[A-Z0-9]{5,7}")) throw new BusinessException("La placa debe tener entre 5 y 7 letras o números");
        if (type == null) throw new BusinessException("Debe elegir el tipo de vehículo");
        boolean inside = tickets.findAll().stream()
                .anyMatch(t -> t.status() == ParkingTicket.Status.ACTIVE && t.plate().equals(p));
        if (inside) throw new BusinessException("El vehículo " + p + " ya se encuentra en el parqueadero");
        ParkingSpot free = spots.findAll().stream().filter(s -> s.type() == type && !s.occupied()).findFirst()
                .orElseThrow(() -> new NoAvailableSpotException(type.label().toLowerCase()));
        ParkingSpot taken = spots.save(free.occupy());
        return tickets.save(new ParkingTicket(null, taken, p, LocalDateTime.now(), null, 0, ParkingTicket.Status.ACTIVE));
    }
}
