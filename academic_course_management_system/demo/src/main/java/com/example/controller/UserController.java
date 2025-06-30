package com.example.controller;

import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return userService.getUserFromToken(token);
    }

    @PutMapping("/update/{idAccount}")
    public User updateUser(@PathVariable Integer idAccount, @RequestBody UserDTO userDTO) {
        return userService.updateUser(idAccount, userDTO);
    }
}