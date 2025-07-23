package com.barberbooking.barberreservationapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User client;

    @ManyToOne
    private Barber barber;

    @ManyToOne
    private Service service;

    private LocalDate date;

    private LocalTime time; // hora de inicio

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private LocalDate createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
        this.status = ReservationStatus.PENDING;
    }
}
