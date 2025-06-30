package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BranchDTO {
    private Long id;
    private Long schoolId;
    @Size(max = 100, message = "Branch name must not exceed 100 characters")
    private String branch;
    @NotBlank(message = "Branch code cannot be empty")
    @Size(max = 50, message = "Branch code must not exceed 50 characters")
    private String branchCode;
}
