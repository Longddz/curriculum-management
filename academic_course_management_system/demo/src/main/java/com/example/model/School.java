package com.example.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Getter
@Setter
@Table(name = "school")
public class School {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "department_code", length = 50, nullable = false, unique = true)
    private String departmentCode;
}