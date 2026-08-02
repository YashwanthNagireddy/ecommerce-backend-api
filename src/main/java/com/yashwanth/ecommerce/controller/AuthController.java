package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.dto.LoginRequest;
import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {
            return ResponseEntity.ok(authService.register(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            String token = authService.login(
                    request.getEmail(),
                    request.getPassword());

            return ResponseEntity.ok(token);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}