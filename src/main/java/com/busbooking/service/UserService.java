package com.busbooking.service;

import com.busbooking.entity.User;
import com.busbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Додати:
    public List<User> searchUsers(String username, String email, String role) {
        return userRepository.findByUsernameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndRoleContainingIgnoreCase(
                username != null ? username : "",
                email != null ? email : "",
                role != null ? role : "");
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Якщо додаси поле "active" для блокування:
    /*
     * public void toggleUserActive(Long id) {
     * User user = userRepository.findById(id).orElseThrow();
     * user.setActive(!user.isActive());
     * userRepository.save(user);
     * }
     */
}