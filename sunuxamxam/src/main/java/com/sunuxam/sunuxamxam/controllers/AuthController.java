package com.sunuxam.sunuxamxam.controllers;

import com.sunuxam.sunuxamxam.dto.LoginRequest;
import com.sunuxam.sunuxamxam.dto.RegisterRequest;
import com.sunuxam.sunuxamxam.entities.Utilisateur;
import com.sunuxam.sunuxamxam.services.AuthService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Utilisateur register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return Map.of("token", token);
    }
}