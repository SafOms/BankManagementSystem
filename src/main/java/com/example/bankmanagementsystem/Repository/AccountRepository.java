package com.example.bankmanagementsystem.Repository;

import com.example.bankmanagementsystem.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // Custom query to find accounts by type
    List<Account> findByAccountType(String accountType);
}