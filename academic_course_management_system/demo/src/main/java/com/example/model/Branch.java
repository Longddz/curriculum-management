package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_school", nullable = false)
    private School school;

    @Column(name = "branch", length = 100)
    @Size(max = 100, message = "Branch name must not exceed 100 characters")
    private String branch;

    @Column(name = "branch_code", length = 50, nullable = false, unique = true)
    @NotBlank(message = "Branch code cannot be empty")
    @Size(max = 50, message = "Branch code must not exceed 50 characters")
    private String branchCode;
}