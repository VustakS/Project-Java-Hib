package com.busbooking.service;

import com.busbooking.entity.Bus;
import com.busbooking.repository.BusRepository;
import com.busbooking.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusService {

    private final BusRepository busRepository;
    private final ReservationRepository reservationRepository;

    public BusService(BusRepository busRepository, ReservationRepository reservationRepository) {
        this.busRepository = busRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Bus> searchBuses(String name, String route, String departureTime) {
        // Далі або делегуй у BusRepository, або фільтруй вручну (краще через
        // репозиторій)
        return busRepository
                .findByNameContainingIgnoreCaseAndRouteStartPointContainingIgnoreCaseAndDepartureTimeContainingIgnoreCase(
                        name != null ? name : "",
                        route != null ? route : "",
                        departureTime != null ? departureTime : "");
    }

    // Для user-панелі — показати всі автобуси з кількістю вільних місць
    public List<Map<String, Object>> getAllBusesWithFreeSeats() {
        List<Bus> buses = busRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Bus bus : buses) {
            int reserved = reservationRepository.getReservedSeatsForBus(bus.getId());
            int freeSeats = bus.getCapacity() - reserved;
            Map<String, Object> map = new HashMap<>();
            map.put("bus", bus);
            map.put("freeSeats", freeSeats);
            result.add(map);
        }
        return result;
    }

    // Для адмінки (CRUD)
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public void saveBus(Bus bus) {
        busRepository.save(bus);
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}