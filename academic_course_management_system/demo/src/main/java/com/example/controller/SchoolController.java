package com.example.controller;

import com.example.dto.SchoolDTO;
import com.example.model.School;
import com.example.service.SchoolService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<School> addDepartment(@Valid @RequestBody SchoolDTO schoolDTO) {
        School school = schoolService.addDepartment(schoolDTO);
        return new ResponseEntity<>(school, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<School> updateDepartment(@PathVariable Long id, @Valid @RequestBody SchoolDTO schoolDTO) {
        schoolDTO.setId(id);
        School updatedSchool = schoolService.updateSchool(schoolDTO);
        return ResponseEntity.ok(updatedSchool);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        schoolService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<School> getDepartmentById(@PathVariable Long id) {
        School school = schoolService.getDepartmentById(id);
        return ResponseEntity.ok(school);
    }

    @GetMapping("/get")
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolService.getAllSchool();
        return ResponseEntity.ok(schools);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}