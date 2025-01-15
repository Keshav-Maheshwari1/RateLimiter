package com.boot.rateLimiter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RateLimitingController {
    @GetMapping("/test")
    public ResponseEntity<String> testRateLimiting(){
        return ResponseEntity.ok("You are currently under a rate limit.");
    }
}
