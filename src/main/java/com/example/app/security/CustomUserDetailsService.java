// package com.example.app.security;

// import com.example.app.model.User;
// import com.example.app.util.ExcelUtil;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Override
//     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//         List<User> users = ExcelUtil.readUsers();
//         return users.stream()
//                 .filter(user -> user.getEmail().equalsIgnoreCase(email))
//                 .findFirst()
//                 .map(CustomUserDetails::new)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
//     }
// }
