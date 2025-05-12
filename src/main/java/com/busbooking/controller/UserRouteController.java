package com.busbooking.controller;

import com.busbooking.entity.Route;
import com.busbooking.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/routes")
public class UserRouteController {

    private final RouteService routeService;

    public UserRouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String viewRoutes(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "startPoint", required = false) String startPoint,
            @RequestParam(value = "endPoint", required = false) String endPoint,
            @RequestParam(value = "stops", required = false) String stops,
            Model model) {
        List<Route> routes = routeService.findByFilter(name, startPoint, endPoint, stops);
        model.addAttribute("routes", routes);
        return "user/routes/view";
    }

}