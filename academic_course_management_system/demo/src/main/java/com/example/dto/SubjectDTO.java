package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectDTO {
    private Long id;
    private Long branchId;
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    private String subject;
    @NotBlank(message = "Subject code cannot be empty")
    @Size(max = 50, message = "Subject code must not exceed 50 characters")
    private String subjectCode;
}