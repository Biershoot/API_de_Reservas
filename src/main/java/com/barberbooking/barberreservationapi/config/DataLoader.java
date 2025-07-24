package com.barberbooking.barberreservationapi.config;

import com.barberbooking.barberreservationapi.entity.*;
import com.barberbooking.barberreservationapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BarberRepository barberRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si no existen
        if (userRepository.count() == 0) {
            loadTestData();
        }
    }

    private void loadTestData() {
        // Crear usuarios
        User client1 = User.builder()
                .name("Juan Pérez")
                .email("juan@email.com")
                .password("123456")
                .role(Role.CLIENT)
                .build();
        
        User client2 = User.builder()
                .name("María García")
                .email("maria@email.com")
                .password("123456")
                .role(Role.CLIENT)
                .build();

        User barberUser1 = User.builder()
                .name("Carlos Martínez")
                .email("carlos@email.com")
                .password("123456")
                .role(Role.BARBER)
                .build();

        User barberUser2 = User.builder()
                .name("Ana López")
                .email("ana@email.com")
                .password("123456")
                .role(Role.BARBER)
                .build();

        // Guardar usuarios
        userRepository.saveAll(List.of(client1, client2, barberUser1, barberUser2));

        // Crear servicios
        com.barberbooking.barberreservationapi.entity.Service service1 = 
            com.barberbooking.barberreservationapi.entity.Service.builder()
                .name("Corte de cabello")
                .durationMinutes(30)
                .price(25.0)
                .build();

        com.barberbooking.barberreservationapi.entity.Service service2 = 
            com.barberbooking.barberreservationapi.entity.Service.builder()
                .name("Barba")
                .durationMinutes(20)
                .price(15.0)
                .build();

        com.barberbooking.barberreservationapi.entity.Service service3 = 
            com.barberbooking.barberreservationapi.entity.Service.builder()
                .name("Corte + Barba")
                .durationMinutes(45)
                .price(35.0)
                .build();

        // Guardar servicios
        serviceRepository.saveAll(List.of(service1, service2, service3));

        // Crear barberos
        Barber barber1 = Barber.builder()
                .user(barberUser1)
                .services(List.of(service1, service2, service3))
                .build();

        Barber barber2 = Barber.builder()
                .user(barberUser2)
                .services(List.of(service1, service3))
                .build();

        // Guardar barberos
        barberRepository.saveAll(List.of(barber1, barber2));

        System.out.println("✅ Datos de prueba cargados exitosamente:");
        System.out.println("👥 Usuarios: " + userRepository.count());
        System.out.println("✂️ Barberos: " + barberRepository.count());
        System.out.println("🛠️ Servicios: " + serviceRepository.count());
    }
} 