package com.busbooking.repository;

import com.busbooking.entity.Bus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByNameContainingIgnoreCaseAndRouteStartPointContainingIgnoreCaseAndDepartureTimeContainingIgnoreCase(
            String name, String routeStart, String departureTime);
}
