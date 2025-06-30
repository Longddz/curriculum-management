package com.example.service;

import com.example.dto.SubjectDTO;
import com.example.model.Subject;
import java.util.List;

public interface SubjectService {
    Subject addSubject(SubjectDTO subjectDTO);
    Subject updateSubject(SubjectDTO subjectDTO);
    boolean deleteSubject(SubjectDTO subjectDTO);
    Subject getSubjectById(Long id);
    List<Subject> getAllSubject();
    List<Subject> getSubjectsByBranchId(Long branchId);
}