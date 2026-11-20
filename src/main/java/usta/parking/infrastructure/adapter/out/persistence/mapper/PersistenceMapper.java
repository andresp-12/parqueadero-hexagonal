package usta.parking.infrastructure.adapter.out.persistence.mapper;
import usta.parking.infrastructure.adapter.out.persistence.entity.*;

import usta.parking.domain.model.*;

/** Convierte entre dominio y entidades JPA. */
public final class PersistenceMapper {
    private PersistenceMapper() {}

    public static ParkingSpot toDomain(SpotEntity e) {
        return new ParkingSpot(e.id, e.code, VehicleType.valueOf(e.type), e.occupied);
    }
    public static SpotEntity toEntity(ParkingSpot d) {
        SpotEntity e = new SpotEntity();
        e.id = d.id(); e.code = d.code(); e.type = d.type().name(); e.occupied = d.occupied();
        return e;
    }
    public static ParkingTicket toDomain(TicketEntity e) {
        return new ParkingTicket(e.id, toDomain(e.spot), e.plate, e.entryTime, e.exitTime, e.amount,
                ParkingTicket.Status.valueOf(e.status));
    }
}
