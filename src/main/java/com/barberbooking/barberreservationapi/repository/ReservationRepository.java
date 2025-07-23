package com.barberbooking.barberreservationapi.repository;

import com.barberbooking.barberreservationapi.entity.Barber;
import com.barberbooking.barberreservationapi.entity.Reservation;
import com.barberbooking.barberreservationapi.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByBarberAndDate(Barber barber, LocalDate date);

    boolean existsByBarberAndDateAndTimeAndStatus(
        Barber barber,
        LocalDate date,
        LocalTime time,
        ReservationStatus status
    );

    List<Reservation> findByDateBetween(LocalDate start, LocalDate end);
}
