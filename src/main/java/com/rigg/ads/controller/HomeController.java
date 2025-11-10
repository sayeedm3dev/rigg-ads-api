package com.rigg.ads.controller;

import com.rigg.ads.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/client-stats")
    public ResponseEntity<Map<String, Long>> getClientStats() {
        Map<String, Long> stats = homeService.getClientStats();
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/campaign-stats")
    public ResponseEntity<Map<String, Long>> getCampaignStats() {
        Map<String, Long> stats = homeService.getCampaignStats();
        return ResponseEntity.ok(stats);
    }
}
