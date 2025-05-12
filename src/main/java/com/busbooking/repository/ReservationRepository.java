package com.busbooking.repository;

import com.busbooking.entity.Reservation;
import com.busbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);

    @Query("SELECT COALESCE(SUM(r.seatsReserved), 0) FROM Reservation r WHERE r.bus.id = :busId")
    int getReservedSeatsForBus(Long busId);

    // Для адмін-пошуку:
    List<Reservation> findByUserUsernameContainingIgnoreCaseAndBusNameContainingIgnoreCase(String username,
            String busName);
}