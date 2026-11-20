package usta.parking.infrastructure.adapter.out.persistence.adapter;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import usta.parking.application.port.out.SpotRepositoryPort;
import usta.parking.domain.model.ParkingSpot;
import usta.parking.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import usta.parking.infrastructure.adapter.out.persistence.repository.SpotRepository;
import java.util.*;

/** Adaptador de salida: implementa el puerto usando JPA (Panache). */
@ApplicationScoped
@Transactional
public class SpotPersistenceAdapter implements SpotRepositoryPort {
    private final SpotRepository repository;
    public SpotPersistenceAdapter(SpotRepository repository) { this.repository = repository; }

    public List<ParkingSpot> findAll() {
        return repository.listAll(Sort.by("id")).stream().map(PersistenceMapper::toDomain).toList();
    }
    public Optional<ParkingSpot> findById(Long id) {
        return Optional.ofNullable(repository.findById(id)).map(PersistenceMapper::toDomain);
    }
    public ParkingSpot save(ParkingSpot spot) {
        return PersistenceMapper.toDomain(repository.getEntityManager().merge(PersistenceMapper.toEntity(spot)));
    }
}
