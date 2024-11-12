package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.AccountService;
import com.example.bankmanagementsystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Scanner;

@RestController
@RequestMapping("/api/accounts")


public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createPersonalAccount(@RequestParam Long userId,
                                                        @RequestParam double initialBalance,
                                                        @RequestParam String accountType) {
        // Directly fetch the user; exception handling will occur if the user is not found
        User user = userService.findById(userId);

        Account account = accountService.createAccount(user, accountType, initialBalance);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountType + " account created with ID: " + account.getId());
    }


    @PutMapping("/{accountId}/deposit")
    public ResponseEntity<String> deposit(@PathVariable Long accountId, @RequestParam double amount) {
        String result = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        String result = accountService.withdraw(accountId, amount);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/apply-interest/{accountId}")
    public ResponseEntity<String> applyInterest(@PathVariable Long accountId) {
        accountService.applyInterest(accountId);
        return ResponseEntity.ok("Interest applied if conditions were met.");
    }


    @DeleteMapping("/{accountId}/close")
    public ResponseEntity<String> closeAccount(@PathVariable Long accountId) {
        String result = accountService.closeAccount(accountId);
        return ResponseEntity.ok(result);
    }

    // Endpoint for transferring funds between accounts
    @PutMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestParam Long fromAccountId,
                                                @RequestParam Long toAccountId,
                                                @RequestParam double amount) {
        String result = accountService.transferBetweenAccounts(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok(result);
    }
}