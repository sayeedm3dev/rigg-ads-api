package com.rigg.ads.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is public";
    }

    @GetMapping("/user")
    public String userEndpoint() {
        return "This is USER endpoint (roles: USER or ADMIN)";
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is ADMIN endpoint (roles: ADMIN)";
    }
}
