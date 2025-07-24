package com.barberbooking.barberreservationapi.controller;

import com.barberbooking.barberreservationapi.dto.ReservationRequest;
import com.barberbooking.barberreservationapi.dto.ReservationResponse;
import com.barberbooking.barberreservationapi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationResponse>> getClientReservations(@PathVariable Long clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByClient(clientId));
    }

    @GetMapping("/barber/{barberId}")
    public ResponseEntity<List<ReservationResponse>> getBarberReservations(@PathVariable Long barberId) {
        return ResponseEntity.ok(reservationService.getReservationsByBarber(barberId));
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
