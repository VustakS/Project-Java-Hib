package com.busbooking.repository;

import com.busbooking.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByNameContainingIgnoreCaseAndStartPointContainingIgnoreCaseAndEndPointContainingIgnoreCaseAndStopsContainingIgnoreCase(
            String name, String startPoint, String endPoint, String stops);
}