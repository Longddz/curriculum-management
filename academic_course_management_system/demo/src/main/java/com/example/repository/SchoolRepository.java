package com.example.repository;

import com.example.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>{
    
    // Kiểm tra xem khoa có tồn tại không thông qua mã khoa 
    boolean existsByDepartmentCode(String department);

    
}
