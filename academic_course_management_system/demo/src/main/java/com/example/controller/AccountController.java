package com.example.controller;

import com.example.dto.AccountDTO;
import com.example.model.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Đăng ký
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody AccountDTO dto) {
        Account account = accountService.addAccount(dto);
        if (account == null) {
            return ResponseEntity.badRequest().body("Gmail already exists");
        }
        return ResponseEntity.ok(account);
    }

    // Đăng nhập
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AccountDTO dto) 
    {
        String token = accountService.login(dto);
        if (token != null)
        {
            return ResponseEntity.ok().body(Map.of("token", token));
    
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Đổi mật khẩu
    @PatchMapping("/auth/change-password")
    public ResponseEntity<?> changePassword(@RequestBody AccountDTO dto) {
        Account account = accountService.updatePassword(dto);
        if (account != null) {
            return ResponseEntity.ok("Password updated successfully");
        }
        return ResponseEntity.status(400).body("Incorrect current password or account not found");
    }
}
