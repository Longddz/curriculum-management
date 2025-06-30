package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_branch", nullable = false)
    private Branch branch;

    @Column(name = "subject", length = 100)
    @Size(max = 100, message = "Subject name must not exceed 100 characters")
    private String subject;

    @Column(name = "subject_code", length = 50, nullable = false, unique = true)
    @NotBlank(message = "Subject code cannot be empty")
    @Size(max = 50, message = "Subject code must not exceed 50 characters")
    private String subjectCode;
}