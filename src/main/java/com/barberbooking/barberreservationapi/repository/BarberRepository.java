package com.barberbooking.barberreservationapi.repository;

import com.barberbooking.barberreservationapi.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {

}
