package com.example.service;

import com.example.dto.SubjectDTO;
import com.example.model.Branch;
import com.example.model.Subject;
import com.example.repository.BranchRepository;
import com.example.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    @Transactional
    public Subject addSubject(SubjectDTO subjectDTO) {
        if (subjectRepository.existsBySubjectCode(subjectDTO.getSubjectCode())) {
            throw new IllegalArgumentException("Subject with code " + subjectDTO.getSubjectCode() + " already exists");
        }

        Branch branch = branchRepository.findById(subjectDTO.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Branch with id " + subjectDTO.getBranchId() + " not found"));

        Subject subject = new Subject();
        subject.setSubject(subjectDTO.getSubject());
        subject.setSubjectCode(subjectDTO.getSubjectCode());
        subject.setBranch(branch);

        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject updateSubject(SubjectDTO subjectDTO) {
        Subject existing = subjectRepository.findById(subjectDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Subject with id " + subjectDTO.getId() + " not found"));

        Branch branch = branchRepository.findById(subjectDTO.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Branch with id " + subjectDTO.getBranchId() + " not found"));

        existing.setSubject(subjectDTO.getSubject());
        existing.setSubjectCode(subjectDTO.getSubjectCode());
        existing.setBranch(branch);

        return subjectRepository.save(existing);
    }

    @Override
    @Transactional
    public boolean deleteSubject(SubjectDTO subjectDTO) {
        if (!subjectRepository.existsById(subjectDTO.getId())) {
            throw new EntityNotFoundException("Subject with id " + subjectDTO.getId() + " not found");
        }
        subjectRepository.deleteById(subjectDTO.getId());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    // Lọc danh sách môn học theo branchId
    @Override
    @Transactional(readOnly = true)
    public List<Subject> getSubjectsByBranchId(Long branchId) {
        return subjectRepository.findByBranchId(branchId);
    }
}