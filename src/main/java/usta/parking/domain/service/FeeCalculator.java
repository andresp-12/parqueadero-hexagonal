package usta.parking.domain.service;

import usta.parking.domain.model.VehicleType;
import java.time.Duration;
import java.time.LocalDateTime;

/** Servicio de dominio: regla de cobro por hora o fracción, mínimo 1 hora. */
public class FeeCalculator {
    public long calculate(VehicleType type, LocalDateTime entry, LocalDateTime exit) {
        long minutes = Duration.between(entry, exit).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60);
        return hours * type.hourlyRate();
    }
}
