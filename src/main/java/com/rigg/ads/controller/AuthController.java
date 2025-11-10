package com.rigg.ads.controller;

import com.rigg.ads.components.JwtUtil;
import com.rigg.ads.dto.AuthRequest;
import com.rigg.ads.dto.AuthResponse;
import com.rigg.ads.dto.RegisterRequest;
import com.rigg.ads.entity.User;
import com.rigg.ads.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request.getUsername(), request.getEmail(), request.getPassword(), request.getRole());
        Long clientId = null;
        if(user.getClient() != null) {
            clientId = user.getClient().getId();
        }        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles(), clientId);

        System.out.println(request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + request.getUsername()));
        Long clientId = null;
        if(user.getClient() != null) {
            clientId = user.getClient().getId();
        }
        String token = jwtUtil.generateToken(request.getUsername(),
                userService.findByUsername(request.getUsername()).get().getRoles(),
                clientId);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
