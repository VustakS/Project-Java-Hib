package com.busbooking.controller;

import com.busbooking.entity.Reservation;
import com.busbooking.entity.User;
import com.busbooking.service.BusService;
import com.busbooking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BusService busService;

    @GetMapping
    public String listReservations(Model model, @AuthenticationPrincipal User currentUser) {
        List<Reservation> reservations = reservationService.getReservationsByUser(currentUser);
        model.addAttribute("reservations", reservations);
        model.addAttribute("currentUser", currentUser);
        return "user/reservations/view";
    }

    @GetMapping("/form")
    public String reservationForm(
            Model model,
            @AuthenticationPrincipal User currentUser,
            @RequestParam(value = "busId", required = false) Long selectedBusId) {
        List<Map<String, Object>> busesWithSeats = busService.getAllBusesWithFreeSeats();
        model.addAttribute("busesWithSeats", busesWithSeats);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("selectedBusId", selectedBusId);
        return "user/reservations/form";
    }

    @PostMapping
    public String createReservation(
            @RequestParam("busId") Long busId,
            @RequestParam("seatsReserved") int seatsReserved,
            @AuthenticationPrincipal User currentUser,
            Model model) {
        try {
            reservationService.createReservation(busId, seatsReserved, currentUser);
            return "redirect:/user/reservations";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Помилка створення резервації: " + e.getMessage());
            model.addAttribute("busesWithSeats", busService.getAllBusesWithFreeSeats());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("selectedBusId", busId);
            return "user/reservations/form";
        }
    }

    @PostMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable("id") Long reservationId,
            @AuthenticationPrincipal User currentUser) {
        // Можеш додати перевірку чи резервація належить currentUser
        reservationService.cancelReservation(reservationId, currentUser);
        return "redirect:/user/reservations";
    }
}