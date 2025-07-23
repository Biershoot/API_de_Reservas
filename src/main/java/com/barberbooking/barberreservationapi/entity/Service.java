package com.barberbooking.barberreservationapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ej: Corte de cabello

    private int durationMinutes;

    private double price;
}

