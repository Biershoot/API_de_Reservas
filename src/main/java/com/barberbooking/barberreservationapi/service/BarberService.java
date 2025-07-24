package com.barberbooking.barberreservationapi.service;

import com.barberbooking.barberreservationapi.entity.Barber;
import com.barberbooking.barberreservationapi.entity.Service;
import com.barberbooking.barberreservationapi.entity.User;
import com.barberbooking.barberreservationapi.repository.BarberRepository;
import com.barberbooking.barberreservationapi.repository.ServiceRepository;
import com.barberbooking.barberreservationapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public Barber createBarber(Long userId, List<Long> serviceIds) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Service> services = serviceRepository.findAllById(serviceIds);

        Barber barber = Barber.builder()
                .user(user)
                .services(services)
                .build();

        return barberRepository.save(barber);
    }

    public List<Barber> getAllBarbers() {
        return barberRepository.findAll();
    }
}
