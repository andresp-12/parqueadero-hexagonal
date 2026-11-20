package usta.parking.domain.model;

import usta.parking.domain.exception.BusinessException;

public record ParkingSpot(Long id, String code, VehicleType type, boolean occupied) {
    public ParkingSpot occupy() {
        if (occupied) throw new BusinessException("El cupo " + code + " ya está ocupado");
        return new ParkingSpot(id, code, type, true);
    }
    public ParkingSpot release() { return new ParkingSpot(id, code, type, false); }
}
