package com.barberbooking.barberreservationapi.dto;

import com.barberbooking.barberreservationapi.entity.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ReservationResponse {
    private Long id;
    private String clientName;
    private String barberName;
    private String serviceName;
    private LocalDate date;
    private LocalTime time;
    private ReservationStatus status;
}