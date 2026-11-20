package usta.parking.domain.model;

import java.time.LocalDateTime;

public record ParkingTicket(Long id, ParkingSpot spot, String plate, LocalDateTime entryTime,
                            LocalDateTime exitTime, long amount, Status status) {
    public enum Status { ACTIVE, CLOSED }

    public ParkingTicket close(LocalDateTime now, long fee) {
        return new ParkingTicket(id, spot, plate, entryTime, now, fee, Status.CLOSED);
    }
}
