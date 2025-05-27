package com.example.app.service;

import com.example.app.model.User;
import com.example.app.util.ExcelUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UserService {
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // 회원가입 처리
    public String register(String email, String rawPassword) {
        List<User> users = ExcelUtil.readUsers();

        boolean exists = users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        if (exists) {
            return "이미 존재하는 이메일입니다.";
        }

        Long newId = users.isEmpty() ? 1L : users.get(users.size() - 1).getId() + 1;
        String hashed = encoder.encode(rawPassword);
        User newUser = new User(newId, email, hashed, "user");

        ExcelUtil.writeUser(newUser);
        return "회원가입 성공";
    }
}
