package com.example.model;

import lombok.Data;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String gmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
