package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CurriculumDTO {

    @NotBlank(message = "Curriculum code is required")
    @Size(max = 20, message = "Curriculum code must not exceed 20 characters")
    private String curriculumCode;

    @Size(max = 50, message = "Identifier must not exceed 50 characters")
    private String identifier;

    @Size(max = 100, message = "Name curriculum must not exceed 100 characters")
    private String nameCurriculum;

    @Size(max = 100, message = "Lecturer must not exceed 100 characters")
    private String lecturer;

    private String description;

    @Size(max = 255, message = "URL must not exceed 255 characters")
    private String url;

    @Size(max = 100, message = "Subject must not exceed 100 characters")
    private String subject;

    @Size(max = 100, message = "Branch must not exceed 100 characters")
    private String branch;

    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @Size(max = 50, message = "Subject code must not exceed 50 characters")
    private String subjectCode;

    @Size(max = 50, message = "Branch code must not exceed 50 characters")
    private String branchCode;

    @Size(max = 50, message = "Department code must not exceed 50 characters")
    private String departmentCode;
}