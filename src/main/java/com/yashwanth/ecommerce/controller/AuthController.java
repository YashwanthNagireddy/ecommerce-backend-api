package com.yashwanth.ecommerce.controller;

import com.yashwanth.ecommerce.dto.LoginRequest;
import com.yashwanth.ecommerce.dto.LoginResponse;
import com.yashwanth.ecommerce.entity.User;
import com.yashwanth.ecommerce.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // =========================
    // REGISTER USER
    // =========================

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody User user
    ) {

        try {

            User registeredUser =
                    authService.register(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(registeredUser);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        try {

            String token = authService.login(
                    request.getEmail(),
                    request.getPassword()
            );

            LoginResponse response =
                    new LoginResponse(token);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }


    // =========================
    // TEMPORARY ADMIN PASSWORD RESET
    // =========================

    @PostMapping("/reset-admin-password")
    public ResponseEntity<?> resetAdminPassword(
            @RequestBody LoginRequest request
    ) {

        try {

            authService.resetAdminPassword(
                    request.getEmail(),
                    request.getPassword()
            );

            return ResponseEntity.ok(
                    "Admin password reset successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}