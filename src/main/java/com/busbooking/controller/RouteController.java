package com.busbooking.controller;

import com.busbooking.entity.Route;
import com.busbooking.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Виведення списку маршрутів з фільтрами
    @GetMapping
    public String listRoutes(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "startPoint", required = false) String startPoint,
            @RequestParam(value = "endPoint", required = false) String endPoint,
            @RequestParam(value = "stops", required = false) String stops,
            Model model) {
        List<Route> routes = routeService.findByFilter(name, startPoint, endPoint, stops);
        model.addAttribute("routes", routes);
        return "admin/routes/list";
    }

    // Форма для створення нового маршруту
    @GetMapping("/new")
    public String createRouteForm(Model model) {
        model.addAttribute("route", new Route());
        return "admin/routes/form";
    }

    // Збереження нового маршруту
    @PostMapping("/save")
    public String saveRoute(@ModelAttribute Route route) {
        routeService.saveRoute(route);
        return "redirect:/admin/routes";
    }

    // Відкриття форми редагування маршруту
    @GetMapping("/edit/{id}")
    public String editRoute(@PathVariable Long id, Model model) {
        Route route = routeService.getRouteById(id);
        model.addAttribute("route", route);
        return "admin/routes/edit";
    }

    // Оновлення маршруту після редагування
    @PostMapping("/update")
    public String updateRoute(@ModelAttribute Route route) {
        routeService.saveRoute(route);
        return "redirect:/admin/routes";
    }

    // Видалення маршруту
    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return "redirect:/admin/routes";
    }
}