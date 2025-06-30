package com.example.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Hàm băm mật khẩu 
    public String encode(String rawPassword)
    {
        return passwordEncoder.encode(rawPassword);
    }

    // Hàm giải mã mật khẩu 
    public boolean matches(String rawPassword, String hashedPassword)
    {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
