package com.barberbooking.barberreservationapi.service;

import com.barberbooking.barberreservationapi.dto.ReservationRequest;
import com.barberbooking.barberreservationapi.dto.ReservationResponse;
import com.barberbooking.barberreservationapi.entity.*;
import com.barberbooking.barberreservationapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public ReservationResponse createReservation(ReservationRequest request) {
        // Validar existencia
        User client = userRepository.findById(request.getClientId()).orElseThrow();
        Barber barber = barberRepository.findById(request.getBarberId()).orElseThrow();
        com.barberbooking.barberreservationapi.entity.Service service = serviceRepository.findById(request.getServiceId()).orElseThrow();

        // Validar disponibilidad
        boolean exists = reservationRepository.existsByBarberAndDateAndTimeAndStatus(
                barber, request.getDate(), request.getTime(), ReservationStatus.CONFIRMED
        );

        if (exists) {
            throw new IllegalStateException("Ese horario ya está reservado.");
        }

        // Crear la reserva
        Reservation reservation = Reservation.builder()
                .client(client)
                .barber(barber)
                .service(service)
                .date(request.getDate())
                .time(request.getTime())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);

        return ReservationResponse.builder()
                .id(reservation.getId())
                .clientName(client.getName())
                .barberName(barber.getUser().getName())
                .serviceName(service.getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .status(reservation.getStatus())
                .build();
    }

    public List<ReservationResponse> getReservationsByClient(Long clientId) {
        return reservationRepository.findAll().stream()
            .filter(r -> r.getClient().getId().equals(clientId))
            .map(this::mapToResponse)
            .toList();
    }

    public List<ReservationResponse> getReservationsByBarber(Long barberId) {
        return reservationRepository.findAll().stream()
            .filter(r -> r.getBarber().getId().equals(barberId))
            .map(this::mapToResponse)
            .toList();
    }

    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la reserva"));

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    private ReservationResponse mapToResponse(Reservation r) {
        return ReservationResponse.builder()
                .id(r.getId())
                .clientName(r.getClient().getName())
                .barberName(r.getBarber().getUser().getName())
                .serviceName(r.getService().getName())
                .date(r.getDate())
                .time(r.getTime())
                .status(r.getStatus())
                .build();
    }
}
