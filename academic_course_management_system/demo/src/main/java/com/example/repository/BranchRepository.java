package com.example.repository;

import com.example.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>{

    // Kiểm tra ngành học có tồn tại không thông qua mã ngành
    boolean existsByBranchCode(String branchCode);

    // Thêm phương thức tìm kiếm trực tiếp theo schoolId
    List<Branch> findBySchoolId(Long schoolId);
}
