package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Exception.InvalidInputException;
import com.example.bankmanagementsystem.Exception.ResourceNotFoundException;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service


public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    // Method to apply interest to a savings account if balance meets threshold
    public void applyInterest(Long accountId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if ("Savings".equals(account.getAccountType()) && account.getBalance() > 1000) {
                double interestAmount = account.getBalance() * account.getInterestRate();
                account.setBalance(account.getBalance() + interestAmount);
                accountRepository.save(account); // Save updated balance
                System.out.println("Interest applied to account ID: " + account.getId());
            } else {
                System.out.println("Interest not applied: Account type is not 'Savings' or balance is below threshold.");
            }
        } else {
            System.out.println("Account not found with ID: " + accountId);
        }
    }

    // This method runs monthly and applies interest to all savings accounts
    @Scheduled(cron = "0 0 0 1 * ?") // Runs at midnight on the first day of each month
    public void applyInterestToAllSavingsAccounts() {
        List<Account> savingsAccounts = accountRepository.findByAccountType("Savings");

        for (Account account : savingsAccounts) {
            if (account.getBalance() > 1000) { // Example threshold
                double interestAmount = account.getBalance() * account.getInterestRate();
                account.setBalance(account.getBalance() + interestAmount);
                accountRepository.save(account);
                System.out.println("Interest applied to account ID: " + account.getId());
            }
        }
    }

    // Transfer funds between two accounts owned by the same user
    public String transferBetweenAccounts(Long fromAccountId, Long toAccountId, double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found with ID: " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found with ID: " + toAccountId));

        // Check if both accounts belong to the same user
        if (!fromAccount.getUser().getId().equals(toAccount.getUser().getId())) {
            throw new InvalidInputException("Transfer failed: Accounts belong to different users.");
        }

        // Check if the balance is sufficient
        if (fromAccount.getBalance() < amount) {
            throw new InvalidInputException("Transfer failed: Insufficient funds in the source account.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return "Transfer successful!";
    }



    public Account createAccount(User user, String accountType, double initialBalance) {
        Account account = new Account(user, accountType, initialBalance);
        return accountRepository.save(account);
    }

    public String deposit(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
        return "Deposit successful!";
    }


    public String withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (account.getBalance() < amount) {
            throw new InvalidInputException("Insufficient funds. Overdraft unavailable.");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
        return "Withdrawal successful!";
    }


    public String closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with ID: " + accountId));

        if (account.getBalance() != 0) {
            throw new InvalidInputException("Account balance must be zero to close. Please visit a branch.");
        }

        accountRepository.delete(account);
        return "Account closed successfully.";
    }


}
