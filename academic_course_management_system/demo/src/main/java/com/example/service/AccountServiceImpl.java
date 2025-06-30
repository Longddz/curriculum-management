// package com.example.service;

// import com.example.dto.AccountDTO;
// import com.example.model.Account;
// import com.example.model.User;
// import com.example.repository.AccountRepository;
// import com.example.repository.UserRepository;
// import com.example.security.JwtTokenProvider;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// @Service
// public class AccountServiceImpl implements AccountService {

//     @Autowired
//     private AccountRepository accountRepository;

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoderService passwordEncoderService;

//     @Autowired
//     private JwtTokenProvider jwtTokenProvider;

//     @Override
//     public boolean existsByGmail(String gmail) {
//         return accountRepository.findByGmail(gmail) != null;
//     }

//     @Override
//     @Transactional
//     public Account addAccount(AccountDTO accountDTO) {
//         if (existsByGmail(accountDTO.getGmail())) return null;

//         // Tạo Account
//         Account account = new Account();
//         account.setGmail(accountDTO.getGmail());
//         account.setPassword(passwordEncoderService.encode(accountDTO.getPassword()));
//         account.setRole(accountDTO.getRole());
//         Account savedAccount = accountRepository.save(account);

//         // Tạo User tương ứng
//         User user = new User();
//         user.setAccount(savedAccount); // Set the Account object directly
//         user.setName(accountDTO.getGmail().split("@")[0]);
//         user.setPosition("Default Position");
//         user.setIdentifier(String.valueOf(savedAccount.getId()));
//         user.setDepartment("Default Department");
//         userRepository.save(user);

//         return savedAccount;
//     }
    
//     @Override
//     public String login(AccountDTO accountDTO) {
//         Account account = accountRepository.findByGmail(accountDTO.getGmail());
//         if (account != null && passwordEncoderService.matches(accountDTO.getPassword(), account.getPassword())) {
//             return jwtTokenProvider.generateToken(account.getGmail(), account.getRole(), account.getId());
//         }
//         return null;
//     }

//     @Override
//     public Account updatePassword(AccountDTO accountDTO) {
//         Account existingAccount = accountRepository.findByGmail(accountDTO.getGmail());
//         if (existingAccount != null &&
//             passwordEncoderService.matches(accountDTO.getPassword(), existingAccount.getPassword()) &&
//             accountDTO.getUpdatePassword() != null) {
//             existingAccount.setPassword(passwordEncoderService.encode(accountDTO.getUpdatePassword()));
//             return accountRepository.save(existingAccount);
//         }
//         return null;
//     }
// }

package com.example.service;

import com.example.dto.AccountDTO;
import com.example.model.Account;
import com.example.model.User;
import com.example.repository.AccountRepository;
import com.example.repository.UserRepository;
import com.example.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean existsByGmail(String gmail) {
        return accountRepository.findByGmail(gmail) != null;
    }

    @Override
    @Transactional
    public Account addAccount(AccountDTO accountDTO) {
        if (existsByGmail(accountDTO.getGmail())) {
            System.out.println("Gmail already exists: " + accountDTO.getGmail());
            return null;
        }

        Account account = new Account();
        account.setGmail(accountDTO.getGmail());
        String encodedPassword = passwordEncoderService.encode(accountDTO.getPassword());
        System.out.println("Encoded password for " + accountDTO.getGmail() + ": " + encodedPassword);
        account.setPassword(encodedPassword);
        account.setRole(accountDTO.getRole());
        Account savedAccount = accountRepository.save(account);

        User user = new User();
        user.setAccount(savedAccount);
        user.setName(accountDTO.getGmail().split("@")[0]);
        user.setPosition("Default Position");
        user.setIdentifier(String.valueOf(savedAccount.getId()));
        user.setDepartment("Default Department");
        userRepository.save(user);

        return savedAccount;
    }

    @Override
    public String login(AccountDTO accountDTO) {
        Account account = accountRepository.findByGmail(accountDTO.getGmail());
        if (account == null) {
            System.out.println("Account not found for gmail: " + accountDTO.getGmail());
            return null;
        }
        if (accountDTO.getPassword() == null) {
            System.out.println("Password is null for gmail: " + accountDTO.getGmail());
            return null;
        }
        if (passwordEncoderService.matches(accountDTO.getPassword(), account.getPassword())) {
            System.out.println("Login successful for gmail: " + accountDTO.getGmail() + " with role: " + account.getRole());
            return jwtTokenProvider.generateToken(account.getGmail(), account.getRole(), account.getId());
        } else {
            System.out.println("Password mismatch for gmail: " + accountDTO.getGmail() + ". Input: " + accountDTO.getPassword() + ", Stored: " + account.getPassword());
            return null;
        }
    }

    @Override
    public Account updatePassword(AccountDTO accountDTO) {
        Account existingAccount = accountRepository.findByGmail(accountDTO.getGmail());
        if (existingAccount != null &&
            passwordEncoderService.matches(accountDTO.getPassword(), existingAccount.getPassword()) &&
            accountDTO.getUpdatePassword() != null) {
            existingAccount.setPassword(passwordEncoderService.encode(accountDTO.getUpdatePassword()));
            return accountRepository.save(existingAccount);
        }
        return null;
    }
}