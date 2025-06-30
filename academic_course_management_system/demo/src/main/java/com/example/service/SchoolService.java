package com.example.service;

import com.example.dto.SchoolDTO;
import com.example.model.School;
import java.util.List;

public interface SchoolService {
    School addDepartment(SchoolDTO schoolDTO);
    School updateSchool(SchoolDTO schoolDTO);
    void deleteDepartment(Long id);
    School getDepartmentById(Long id);
    List<School> getAllSchool();
}