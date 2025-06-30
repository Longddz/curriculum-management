package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id_account")
    private Integer idAccount;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_account")
    private Account account;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Column(name = "position", length = 50)
    @Size(max = 50, message = "Position must not exceed 50 characters")
    private String position;

    @Column(name = "identifier", unique = true, length = 50)
    @Size(max = 50, message = "Identifier must not exceed 50 characters")
    private String identifier;

    @Column(name = "department", length = 100)
    @Size(max = 100, message = "Department must not exceed 100 characters")
    private String department;

    @ManyToOne
    @JoinColumn(name = "id_school")
    private School school;
}