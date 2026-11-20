package usta.parking.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_spots")
public class SpotEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false) public String code;
    @Column(nullable = false) public String type;
    @Column(nullable = false) public boolean occupied;
}
