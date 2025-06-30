package com.example.service;

import com.example.dto.BranchDTO;
import com.example.model.Branch;
import com.example.model.School;
import com.example.repository.BranchRepository;
import com.example.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    // Thêm ngành mới 
    @Override
    @Transactional
    public Branch addBranch(BranchDTO branchDTO) {
        if (branchRepository.existsByBranchCode(branchDTO.getBranchCode())) {
            throw new IllegalArgumentException("Branch with code " + branchDTO.getBranchCode() + " already exists");
        }

        School school = schoolRepository.findById(branchDTO.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("School with id " + branchDTO.getSchoolId() + " not found"));

        Branch branch = new Branch();
        branch.setBranch(branchDTO.getBranch());
        branch.setBranchCode(branchDTO.getBranchCode());
        branch.setSchool(school);

        return branchRepository.save(branch);
    }

    // Cập nhật ngành 
    @Override
    @Transactional
    public Branch updateBranch(BranchDTO branchDTO) {
        Branch existing = branchRepository.findById(branchDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Branch with id " + branchDTO.getId() + " not found"));

        School school = schoolRepository.findById(branchDTO.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("School with id " + branchDTO.getSchoolId() + " not found"));

        existing.setBranch(branchDTO.getBranch());
        existing.setBranchCode(branchDTO.getBranchCode());
        existing.setSchool(school);

        return branchRepository.save(existing);
    }

    // Xóa ngành 
    @Override
    @Transactional
    public void deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new EntityNotFoundException("Branch with id " + id + " not found");
        }
        branchRepository.deleteById(id);
    }

    // Lấy thông tin ngành thông qua id 
    @Override
    @Transactional(readOnly = true)
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch with id " + id + " not found"));
    }

    // Lấy danh sách ngành 
    @Override
    @Transactional(readOnly = true)
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    // Lọc danh sách ngành theo schoolId
    @Override
    @Transactional(readOnly = true)
    public List<Branch> getBranchesBySchoolId(Long schoolId) {
        return branchRepository.findBySchoolId(schoolId);
    }
}