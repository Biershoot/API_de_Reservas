package com.barberbooking.barberreservationapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportResponse {
    private int totalReservations;
    private double totalIncome;
}
