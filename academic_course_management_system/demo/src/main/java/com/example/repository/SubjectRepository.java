package com.example.repository;

import com.example.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{

    // Kiểm tra môn học có tồn tại không thông qua mã môn
    boolean existsBySubjectCode(String subjectCode);

    // Thêm phương thức tìm kiếm trực tiếp theo branchId
    List<Subject> findByBranchId(Long branchId);
}
