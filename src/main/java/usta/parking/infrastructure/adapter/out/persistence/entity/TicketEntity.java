package usta.parking.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_tickets")
public class TicketEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "spot_id")
    public SpotEntity spot;
    @Column(nullable = false) public String plate;
    @Column(name = "entry_time", nullable = false) public LocalDateTime entryTime;
    @Column(name = "exit_time") public LocalDateTime exitTime;
    @Column(nullable = false) public long amount;
    @Column(nullable = false) public String status;
}
