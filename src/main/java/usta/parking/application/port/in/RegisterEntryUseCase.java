package usta.parking.application.port.in;

import usta.parking.domain.model.*;

public interface RegisterEntryUseCase {
    ParkingTicket execute(String plate, VehicleType type);
}
