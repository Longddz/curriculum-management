package com.example.service;

import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.model.School;
import com.example.repository.UserRepository;
import com.example.repository.SchoolRepository;
import com.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public User getUserFromToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Integer idAccount = jwtTokenProvider.getIdAccountFromToken(token);
        if (idAccount == null) {
            throw new IllegalArgumentException("idAccount not found in token");
        }

        return userRepository.findById(idAccount)
                .orElseThrow(() -> new IllegalArgumentException("User not found for idAccount: " + idAccount));
    }

    @Override
    @Transactional
    public User updateUser(Integer idAccount, UserDTO userDTO) {
        User user = userRepository.findById(idAccount)
                .orElseThrow(() -> new IllegalArgumentException("User not found for idAccount: " + idAccount));

        if (userDTO.getIdentifier() != null && !userDTO.getIdentifier().equals(user.getIdentifier())) {
            if (userRepository.existsByIdentifier(userDTO.getIdentifier())) {
                throw new IllegalArgumentException("Identifier already exists: " + userDTO.getIdentifier());
            }
            user.setIdentifier(userDTO.getIdentifier());
        }

        if (userDTO.getIdSchool() != null) {
            School school = schoolRepository.findById(userDTO.getIdSchool())
                    .orElseThrow(() -> new EntityNotFoundException("Department with id " + userDTO.getIdSchool() + " not found"));
            user.setSchool(school);
        }

        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getPosition() != null) {
            user.setPosition(userDTO.getPosition());
        }
        if (userDTO.getDepartment() != null) {
            user.setDepartment(userDTO.getDepartment());
        }

        return userRepository.save(user);
    }
}