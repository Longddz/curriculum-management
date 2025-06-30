package com.example.repository;

import com.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    // Tìm kiếm tài khoản theo Gmail
    Account findByGmail(String gmail);
}
