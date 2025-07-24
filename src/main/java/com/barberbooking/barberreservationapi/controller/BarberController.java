package com.barberbooking.barberreservationapi.controller;

import com.barberbooking.barberreservationapi.entity.Barber;
import com.barberbooking.barberreservationapi.service.BarberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService barberService;

    @PostMapping
    public ResponseEntity<Barber> createBarber(@RequestBody CreateBarberRequest request) {
        return ResponseEntity.ok(barberService.createBarber(request.getUserId(), request.getServiceIds()));
    }

    @GetMapping
    public ResponseEntity<List<Barber>> getAllBarbers() {
        return ResponseEntity.ok(barberService.getAllBarbers());
    }

    @Data
    public static class CreateBarberRequest {
        private Long userId;
        private List<Long> serviceIds;
    }
}
