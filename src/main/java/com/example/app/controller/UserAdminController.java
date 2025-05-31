package com.example.app.controller;

import com.example.app.entity.User;
import com.example.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import com.example.app.entity.User;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class UserAdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
    
        User requester = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("사용자 없음"));
    
        if (!requester.isAdmin()) {
            return ResponseEntity.status(403).body("접근 권한 없음");
        }
    
        return ResponseEntity.ok(userRepository.findAll());
    }
    

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
    
        User requester = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("사용자 없음"));
    
        if (!requester.isAdmin()) {
            return ResponseEntity.status(403).body("접근 권한 없음");
        }
    
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
    
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    // @GetMapping("/me")
    // public ResponseEntity<?> getCurrentUser(Authentication authentication) {
    //     String email = authentication.getName();
    //     Optional<User> userOpt = userRepository.findByEmail(email);
    
    //     if (userOpt.isPresent()) {
    //         return ResponseEntity.ok(userOpt.get());
    //     } else {
    //         return ResponseEntity.status(404).body("사용자 없음");
    //     }
    // }
    
    
    
}
