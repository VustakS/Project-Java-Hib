package com.busbooking.controller;

import com.busbooking.service.BusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/buses")
public class UserBusController {

    private final BusService busService;

    public UserBusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public String listBuses(Model model) {
        model.addAttribute("busesWithSeats", busService.getAllBusesWithFreeSeats());
        return "user/buses/view";
    }
}