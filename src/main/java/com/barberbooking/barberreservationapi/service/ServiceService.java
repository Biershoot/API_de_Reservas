package com.barberbooking.barberreservationapi.service;

import com.barberbooking.barberreservationapi.entity.Service;
import com.barberbooking.barberreservationapi.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }
}   