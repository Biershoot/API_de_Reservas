package com.barberbooking.barberreservationapi.service;

import com.barberbooking.barberreservationapi.dto.ReportResponse;
import com.barberbooking.barberreservationapi.entity.Reservation;
import com.barberbooking.barberreservationapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReservationRepository reservationRepository;

    public ReportResponse reportByDate(LocalDate start, LocalDate end) {
        List<Reservation> reservations = reservationRepository.findByDateBetween(start, end);
        return buildReport(reservations);
    }

    public ReportResponse reportByBarber(Long barberId, LocalDate start, LocalDate end) {
        List<Reservation> reservations = reservationRepository.findByBarberIdAndDateBetween(barberId, start, end);
        return buildReport(reservations);
    }

    public ReportResponse reportByService(Long serviceId, LocalDate start, LocalDate end) {
        List<Reservation> reservations = reservationRepository.findByServiceIdAndDateBetween(serviceId, start, end);
        return buildReport(reservations);
    }

    private ReportResponse buildReport(List<Reservation> reservations) {
        int total = reservations.size();
        double income = reservations.stream()
                .mapToDouble(r -> r.getService().getPrice())
                .sum();

        return ReportResponse.builder()
                .totalReservations(total)
                .totalIncome(income)
                .build();
    }
}
