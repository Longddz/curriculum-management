package com.example.repository;

import com.example.model.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Curriculum entities.
 */
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, String>, JpaSpecificationExecutor<Curriculum> {

    Curriculum findByCurriculumCode(String code);

    // List<Curriculum> findByNameCurriculumContainingIgnoreCaseAndLecturerContainingIgnoreCaseAndIdentifierContainingIgnoreCaseAndSubjectContainingIgnoreCaseAndBranchContainingIgnoreCaseAndDepartmentContainingIgnoreCase(
    //         String name, String lecturer, String identifier, String subject, String branch, String department);

    boolean existsByCurriculumCode(String code);

    
}