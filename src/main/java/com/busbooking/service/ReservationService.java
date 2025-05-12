package com.busbooking.service;

import com.busbooking.entity.Bus;
import com.busbooking.entity.Reservation;
import com.busbooking.entity.User;
import com.busbooking.repository.BusRepository;
import com.busbooking.repository.ReservationRepository;
import com.busbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    // Для користувача (автентифікованого)
    public void createReservation(Long busId, int seatsReserved, User user) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found!"));

        int reservedSeats = reservationRepository.getReservedSeatsForBus(busId);
        int freeSeats = bus.getCapacity() - reservedSeats;

        if (seatsReserved > freeSeats) {
            throw new IllegalArgumentException("Недостатньо вільних місць! Доступно: " + freeSeats);
        }
        if (seatsReserved < 1) {
            throw new IllegalArgumentException("Кількість місць має бути більше 0!");
        }

        Reservation reservation = new Reservation();
        reservation.setBus(bus);
        reservation.setUser(user);
        reservation.setSeatsReserved(seatsReserved);
        reservationRepository.save(reservation);
    }

    // Для адміна: створити резервацію для будь-якого користувача
    public void createReservationForAdmin(Long userId, Long busId, int seatsReserved) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
        createReservation(busId, seatsReserved, user);
    }

    // Повертає всі резервації (для адміна)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    // Пошук за юзером і автобусом (для адміна)
    public List<Reservation> searchReservations(String username, String busName) {
        if ((username == null || username.isEmpty()) && (busName == null || busName.isEmpty())) {
            return reservationRepository.findAll();
        }
        return reservationRepository.findByUserUsernameContainingIgnoreCaseAndBusNameContainingIgnoreCase(
                username != null ? username : "",
                busName != null ? busName : "");
    }

    // Для користувача - його резервації
    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    // Видалення (для адміна)
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    // Скасування (для користувача)
    public void cancelReservation(Long reservationId, User user) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        if (!reservation.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You can't cancel this reservation");
        }
        reservationRepository.delete(reservation);
    }
}