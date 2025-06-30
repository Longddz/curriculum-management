package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Lấy về thông tin người dùng 
    User findByIdentifier(String identifier);

    // Kiểm tra xem người dùng có tồn tại không
    boolean existsByIdentifier(String identifier);
}