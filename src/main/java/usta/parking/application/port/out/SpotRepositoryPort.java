package usta.parking.application.port.out;

import usta.parking.domain.model.ParkingSpot;
import java.util.*;

public interface SpotRepositoryPort {
    List<ParkingSpot> findAll();
    Optional<ParkingSpot> findById(Long id);
    ParkingSpot save(ParkingSpot spot);
}
