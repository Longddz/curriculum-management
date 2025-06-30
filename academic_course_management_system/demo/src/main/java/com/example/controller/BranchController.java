package com.example.controller;

import com.example.dto.BranchDTO;
import com.example.model.Branch;
import com.example.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Branch> addBranch(@Valid @RequestBody BranchDTO branchDTO) {
        Branch branch = branchService.addBranch(branchDTO);
        return new ResponseEntity<>(branch, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @Valid @RequestBody BranchDTO branchDTO) {
        branchDTO.setId(id); 
        Branch updatedBranch = branchService.updateBranch(branchDTO);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_DEAN')")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Branch>> getAllBranches(@RequestParam(required = false) Long schoolId) {
        List<Branch> branches = (schoolId != null) ? branchService.getBranchesBySchoolId(schoolId) : branchService.getAllBranches();
        return ResponseEntity.ok(branches);
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