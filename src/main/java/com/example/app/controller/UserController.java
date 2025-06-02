package com.example.app.controller;

import com.example.app.entity.User;
import com.example.app.repository.UserRepository;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String hashed = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashed);
        userRepository.save(user);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        return userRepository.findByEmail(loginUser.getEmail())
            .map(user -> {
                if (passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                    return ResponseEntity.ok("로그인 성공");
                } else {
                    return ResponseEntity.status(401).body("비밀번호 불일치");
                }
            })
            .orElseGet(() -> ResponseEntity.status(401).body("존재하지 않는 이메일"));
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userRepository.existsByEmail(email);
        return ResponseEntity.ok(exists);  // true = 이미 있음, false = 사용 가능
    }
    

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(401).body("인증되지 않은 사용자");
        }
    
        String email = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(email);
    
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(404).body("사용자 없음");
        }
    }
    

}
