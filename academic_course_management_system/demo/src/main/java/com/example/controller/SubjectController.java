package com.example.controller;

import com.example.dto.SubjectDTO;
import com.example.model.Subject;
import com.example.service.SubjectService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Subject> addSubject(@Valid @RequestBody SubjectDTO subjectDTO) {
        Subject subject = subjectService.addSubject(subjectDTO);
        return new ResponseEntity<>(subject, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectDTO subjectDTO) {
        subjectDTO.setId(id); 
        Subject updatedSubject = subjectService.updateSubject(subjectDTO);
        return ResponseEntity.ok(updatedSubject);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(id);
        subjectService.deleteSubject(subjectDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Subject> getSubjectById(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Subject>> getAllSubjects(@RequestParam(required = false) Long branchId) {
        List<Subject> subjects = (branchId != null) ? subjectService.getSubjectsByBranchId(branchId) : subjectService.getAllSubject();
        return ResponseEntity.ok(subjects);
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