package com.example.service;

import com.example.dto.AccountDTO;
import com.example.model.Account;

public interface AccountService {
    Account addAccount(AccountDTO accountDTO);
    String login(AccountDTO accountDTO);
    Account updatePassword(AccountDTO accountDTO);
    boolean existsByGmail(String gmail);
}
