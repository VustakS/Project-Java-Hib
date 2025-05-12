package com.busbooking.service;

import com.busbooking.entity.Route;
import com.busbooking.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public void saveRoute(Route route) {
        routeRepository.save(route);
    }

    public Route getRouteById(Long routeId) {
        return routeRepository.findById(routeId).orElse(null);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    // === Для фільтрів ===
    public List<Route> findByFilter(String name, String startPoint, String endPoint, String stops) {
        return routeRepository
                .findByNameContainingIgnoreCaseAndStartPointContainingIgnoreCaseAndEndPointContainingIgnoreCaseAndStopsContainingIgnoreCase(
                        name != null ? name : "",
                        startPoint != null ? startPoint : "",
                        endPoint != null ? endPoint : "",
                        stops != null ? stops : "");
    }
}