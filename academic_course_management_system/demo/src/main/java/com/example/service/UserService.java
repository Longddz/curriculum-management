package com.example.service;

import com.example.dto.UserDTO;
import com.example.model.User;

public interface UserService {
    User getUserFromToken(String token);
    User updateUser(Integer idAccount, UserDTO userDTO);
}