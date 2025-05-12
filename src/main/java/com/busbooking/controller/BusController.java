package com.busbooking.controller;

import com.busbooking.entity.Bus;
import com.busbooking.entity.Route;
import com.busbooking.service.BusService;
import com.busbooking.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/buses")
public class BusController {

    private final BusService busService;
    private final RouteService routeService;

    public BusController(BusService busService, RouteService routeService) {
        this.busService = busService;
        this.routeService = routeService;
    }

    // Виведення списку автобусів
    @GetMapping
    public String listBuses(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "route", required = false) String route,
            @RequestParam(value = "departureTime", required = false) String departureTime,
            Model model) {
        List<Bus> buses = busService.searchBuses(name, route, departureTime);
        model.addAttribute("buses", buses);
        return "admin/buses/list";
    }

    // Форма для створення нового автобуса
    @GetMapping("/new")
    public String createBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        model.addAttribute("routes", routeService.getAllRoutes()); // Передати маршрути у модель
        return "admin/buses/form"; // форма для створення автобуса
    }

    @PostMapping("/save")
    public String saveBus(@ModelAttribute Bus bus, @RequestParam Long routeId) {
        Route selectedRoute = routeService.getRouteById(routeId);
        bus.setRoute(selectedRoute);
        busService.saveBus(bus);
        return "redirect:/admin/buses";
    }

    // Відкриття форми редагування автобуса
    @GetMapping("/edit/{id}")
    public String editBus(@PathVariable Long id, Model model) {
        Bus bus = busService.getBusById(id);
        model.addAttribute("bus", bus);
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("routes", routes); // маршрути для вибору
        return "admin/buses/edit"; // окрема сторінка редагування
    }

    // Оновлення автобуса після редагування
    @PostMapping("/update")
    public String updateBus(@ModelAttribute Bus bus) {
        busService.saveBus(bus);
        return "redirect:/admin/buses";
    }

    // Видалення автобуса
    @GetMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return "redirect:/admin/buses";
    }

}
