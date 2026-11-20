package usta.parking.infrastructure.adapter.out.persistence.adapter;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import usta.parking.application.port.out.TicketRepositoryPort;
import usta.parking.domain.model.ParkingTicket;
import usta.parking.infrastructure.adapter.out.persistence.entity.TicketEntity;
import usta.parking.infrastructure.adapter.out.persistence.mapper.PersistenceMapper;
import usta.parking.infrastructure.adapter.out.persistence.repository.*;
import java.util.*;

/** Adaptador de salida: implementa el puerto usando JPA (Panache). */
@ApplicationScoped
@Transactional
public class TicketPersistenceAdapter implements TicketRepositoryPort {
    private final TicketRepository repository;
    private final SpotRepository spotRepository;

    public TicketPersistenceAdapter(TicketRepository repository, SpotRepository spotRepository) {
        this.repository = repository; this.spotRepository = spotRepository;
    }

    public List<ParkingTicket> findAll() {
        return repository.listAll(Sort.descending("id")).stream().map(PersistenceMapper::toDomain).toList();
    }
    public Optional<ParkingTicket> findById(Long id) {
        return Optional.ofNullable(repository.findById(id)).map(PersistenceMapper::toDomain);
    }
    public ParkingTicket save(ParkingTicket t) {
        TicketEntity e = t.id() == null ? new TicketEntity() : repository.findById(t.id());
        e.spot = spotRepository.findById(t.spot().id());
        e.plate = t.plate(); e.entryTime = t.entryTime(); e.exitTime = t.exitTime();
        e.amount = t.amount(); e.status = t.status().name();
        if (e.id == null) repository.persist(e);
        return PersistenceMapper.toDomain(e);
    }
}
