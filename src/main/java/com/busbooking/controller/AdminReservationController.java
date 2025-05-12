package com.busbooking.controller;

import com.busbooking.entity.Reservation;
import com.busbooking.entity.User;
import com.busbooking.entity.Bus;
import com.busbooking.service.ReservationService;
import com.busbooking.service.UserService;
import com.busbooking.service.BusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final BusService busService;

    public AdminReservationController(ReservationService reservationService, UserService userService,
            BusService busService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.busService = busService;
    }

    // Перегляд усіх резервацій з фільтрами
    @GetMapping
    public String listReservations(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "busName", required = false) String busName,
            Model model) {
        List<Reservation> reservations = reservationService.searchReservations(username, busName);
        model.addAttribute("reservations", reservations);
        return "admin/reservations/list";
    }

    // Форма створення резервації
    @GetMapping("/form")
    public String reservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("buses", busService.getAllBuses());
        return "admin/reservations/form";
    }

    // Створення резервації
    @PostMapping("/form")
    public String createReservation(@ModelAttribute Reservation reservation, @RequestParam Long userId,
            @RequestParam Long busId) {
        reservationService.createReservationForAdmin(userId, busId, reservation.getSeatsReserved());
        return "redirect:/admin/reservations";
    }

    // Видалення резервації
    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/admin/reservations";
    }
}