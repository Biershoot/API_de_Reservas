package com.barberbooking.barberreservationapi.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ReservationRequest {
    private Long clientId;
    private Long barberId;
    private Long serviceId;
    private LocalDate date;
    private LocalTime time; // hora de inicio
}
