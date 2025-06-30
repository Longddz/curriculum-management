package com.example.service;

import com.example.dto.BranchDTO;
import com.example.model.Branch;
import java.util.List;

public interface BranchService {
    Branch addBranch(BranchDTO branchDTO);
    Branch updateBranch(BranchDTO branchDTO);
    void deleteBranch(Long id);
    Branch getBranchById(Long id);
    List<Branch> getAllBranches();
    List<Branch> getBranchesBySchoolId(Long schoolId);
}