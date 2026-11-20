package usta.parking.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import usta.parking.application.port.in.*;
import usta.parking.application.port.out.*;
import usta.parking.application.usecase.*;
import usta.parking.domain.service.FeeCalculator;

/** Conecta los casos de uso (sin anotaciones) con el contenedor CDI. */
public class BeanConfig {
    @Produces @ApplicationScoped
    public RegisterEntryUseCase registerEntry(SpotRepositoryPort s, TicketRepositoryPort t) {
        return new RegisterEntryUseCaseImpl(s, t);
    }
    @Produces @ApplicationScoped
    public RegisterExitUseCase registerExit(SpotRepositoryPort s, TicketRepositoryPort t) {
        return new RegisterExitUseCaseImpl(s, t, new FeeCalculator());
    }
    @Produces @ApplicationScoped
    public QueryParkingUseCase query(SpotRepositoryPort s, TicketRepositoryPort t) {
        return new QueryParkingUseCaseImpl(s, t);
    }
}
