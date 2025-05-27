package com.example.app.controller;

import com.example.app.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return userService.register(request.getEmail(), request.getPassword());
    }

    @Data
    public static class RegisterRequest {
        private String email;
        private String password;
    }
}
