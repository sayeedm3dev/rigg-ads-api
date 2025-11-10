package com.rigg.ads.controller;

import com.rigg.ads.entity.User;
import com.rigg.ads.service.UserService;
import com.rigg.ads.components.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public ProfileController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Prefill profile
    @GetMapping
    public ResponseEntity<User> getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long clientId = jwtUtil.getClientIdFromToken(token);

        User user = userService.getUserByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(null);
        return ResponseEntity.ok(user);
    }


    // Save profile changes
    @PutMapping
    public ResponseEntity<User> updateProfile(HttpServletRequest request, @RequestBody User updatedUser) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long clientId = jwtUtil.getClientIdFromToken(token);

        User currentUser = userService.getUserByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User savedUser = userService.updateUserProfile(
                currentUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getPassword()
        );

        savedUser.setPassword(null); // hide password
        return ResponseEntity.ok(savedUser);
    }
}

