package com.barberbooking.barberreservationapi.repository;

import com.barberbooking.barberreservationapi.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}