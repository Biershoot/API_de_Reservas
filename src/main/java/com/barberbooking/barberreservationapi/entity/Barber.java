package com.barberbooking.barberreservationapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "barbers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
        name = "barber_services",
        joinColumns = @JoinColumn(name = "barber_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;
}
