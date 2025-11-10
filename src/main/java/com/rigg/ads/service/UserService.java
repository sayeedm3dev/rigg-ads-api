package com.rigg.ads.service;

import com.rigg.ads.entity.Role;
import com.rigg.ads.entity.User;
import com.rigg.ads.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.userRepository = repo;
        this.passwordEncoder = encoder;
    }

    public User register(String username, String email, String rawPassword, String roleStr) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already in use");
        }

        Set<Role> roles = new HashSet<>();
        try {
            roles.add(Role.valueOf(roleStr));
        } catch (Exception e) {
            roles.add(Role.ROLE_USER);
        }

        User u = new User(username, email, passwordEncoder.encode(rawPassword), roles);
        return userRepository.save(u);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
