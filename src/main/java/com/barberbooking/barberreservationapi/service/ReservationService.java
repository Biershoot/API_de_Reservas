package com.barberbooking.barberreservationapi.service;

import com.barberbooking.barberreservationapi.dto.ReservationRequest;
import com.barberbooking.barberreservationapi.dto.ReservationResponse;
import com.barberbooking.barberreservationapi.entity.*;
import com.barberbooking.barberreservationapi.repository.*;
import com.barberbooking.barberreservationapi.util.InvoicePdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio principal para gestionar reservas de la barbería.
 * Incluye lógica para crear, cancelar y consultar reservas.
 * Al crear una reserva, se genera y envía una factura PDF automáticamente.
 */
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

    @Autowired
    private InvoicePdfGenerator pdfGenerator;

    @Autowired
    private EmailService emailService;

    /**
     * Crea una nueva reserva, valida disponibilidad y envía la factura por correo.
     * @param request datos de la reserva
     * @return respuesta con los datos de la reserva confirmada
     */
    public ReservationResponse createReservation(ReservationRequest request) {
        // Validar existencia de cliente, barbero y servicio
        User client = userRepository.findById(request.getClientId()).orElseThrow();
        Barber barber = barberRepository.findById(request.getBarberId()).orElseThrow();
        com.barberbooking.barberreservationapi.entity.Service service = serviceRepository.findById(request.getServiceId()).orElseThrow();

        // Validar disponibilidad del barbero en la fecha y hora solicitada
        boolean exists = reservationRepository.existsByBarberAndDateAndTimeAndStatus(
                barber, request.getDate(), request.getTime(), ReservationStatus.CONFIRMED
        );

        if (exists) {
            throw new IllegalStateException("Ese horario ya está reservado.");
        }

        // Construir la entidad Reservation
        Reservation reservation = Reservation.builder()
                .client(client)
                .barber(barber)
                .service(service)
                .date(request.getDate())
                .time(request.getTime())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);

        // Generar PDF y enviar correo con la factura al cliente
        try {
            byte[] pdfBytes = pdfGenerator.generate(reservation);
            emailService.sendInvoice(client.getEmail(), pdfBytes, "factura_reserva_" + reservation.getId() + ".pdf");
        } catch (Exception e) {
            // Loguea el error pero no interrumpe el flujo principal
            e.printStackTrace();
        }

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

    /**
     * Obtiene todas las reservas de un cliente.
     */
    public List<ReservationResponse> getReservationsByClient(Long clientId) {
        return reservationRepository.findAll().stream()
            .filter(r -> r.getClient().getId().equals(clientId))
            .map(this::mapToResponse)
            .toList();
    }

    /**
     * Obtiene todas las reservas de un barbero.
     */
    public List<ReservationResponse> getReservationsByBarber(Long barberId) {
        return reservationRepository.findAll().stream()
            .filter(r -> r.getBarber().getId().equals(barberId))
            .map(this::mapToResponse)
            .toList();
    }

    /**
     * Cancela una reserva existente.
     */
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la reserva"));

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    /**
     * Convierte una entidad Reservation a su DTO de respuesta.
     */
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
