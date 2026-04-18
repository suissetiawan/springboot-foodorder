package com.dibimbing.foodorder.controller;

import com.dibimbing.foodorder.dto.AuthDTO;
import com.dibimbing.foodorder.dto.BaseResponse;
import com.dibimbing.foodorder.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AuthDTO.RegisterResponse>> register(
            @Valid @RequestBody AuthDTO.RegisterRequest request) {
        return ResponseEntity.ok(
                BaseResponse.<AuthDTO.RegisterResponse>builder()
                        .message("User registered successfully")
                        .data(authService.register(request))
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthDTO.LoginResponse>> authenticate(
            @Valid @RequestBody AuthDTO.LoginRequest request) {
        return ResponseEntity.ok(
                BaseResponse.<AuthDTO.LoginResponse>builder()
                        .message("Login successful")
                        .data(authService.authenticate(request))
                        .build());
    }
}
