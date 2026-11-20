package usta.parking.infrastructure.adapter.out.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import usta.parking.infrastructure.adapter.out.persistence.entity.TicketEntity;

@ApplicationScoped
public class TicketRepository implements PanacheRepository<TicketEntity> {}
