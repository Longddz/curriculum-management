package com.example.service;

import com.example.dto.SchoolDTO;
import com.example.model.School;
import com.example.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    // Thêm một khoa mới dựa trên thông tin từ DTO.
    @Override
    @Transactional
    public School addDepartment(SchoolDTO schoolDTO) {
        if (schoolRepository.existsByDepartmentCode(schoolDTO.getDepartmentCode())) {
            throw new IllegalArgumentException("Department with code " + schoolDTO.getDepartmentCode() + " already exists");
        }

        School school = new School();
        school.setDepartmentCode(schoolDTO.getDepartmentCode());
        school.setDepartment(schoolDTO.getDepartment());

        return schoolRepository.save(school);
    }

    // Cập nhật thông tin khoa dựa trên ID.
    @Override
    @Transactional
    public School updateSchool(SchoolDTO schoolDTO) {
        School existing = schoolRepository.findById(schoolDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + schoolDTO.getId() + " not found"));

        existing.setDepartmentCode(schoolDTO.getDepartmentCode());
        existing.setDepartment(schoolDTO.getDepartment());

        return schoolRepository.save(existing);
    }

    // Xóa khoa dựa trên ID.
    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new EntityNotFoundException("Department with id " + id + " not found");
        }
        schoolRepository.deleteById(id);
    }

    // Lấy thông tin khoa theo ID.
    @Override
    @Transactional(readOnly = true)
    public School getDepartmentById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + id + " not found"));
    }

    // Lấy danh sách tất cả các khoa.
    @Override
    @Transactional(readOnly = true)
    public List<School> getAllSchool() {
        return schoolRepository.findAll();
    }
}