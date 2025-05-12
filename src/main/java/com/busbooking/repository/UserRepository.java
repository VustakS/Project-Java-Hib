package com.busbooking.repository;

import com.busbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoleContainingIgnoreCase(
            String username, String email, String role);
}
