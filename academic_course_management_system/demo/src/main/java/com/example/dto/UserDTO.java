package com.example.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String position;
    private String identifier;
    private String department;
    private Long idSchool;
}