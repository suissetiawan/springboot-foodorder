package com.dibimbing.foodorder.dto;

import com.dibimbing.foodorder.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class AuthDTO {

    @Data
    public static class LoginRequest {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank
        private String username;
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
        private UserRole role;
    }

    @Data
    public static class LoginResponse {
        private String username;
        private String email;
        private UserRole role;
        private String token;
    }

    @Data
    public static class RegisterResponse {
        private String username;
        private String email;
        private UserRole role;
    }
}
