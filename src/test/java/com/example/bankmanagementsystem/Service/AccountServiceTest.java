package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ShouldReturnCreatedAccount() {
        User user = new User("John Doe", "john@example.com", "123 Main St");
        Account account = new Account(user, "Personal", 1000.0);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(user, "Personal", 1000.0);

        assertNotNull(createdAccount);
        assertEquals("Personal", createdAccount.getAccountType());
        assertEquals(1000.0, createdAccount.getBalance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void deposit_ShouldIncreaseBalance_WhenAccountExists() {
        Account account = new Account(new User("Jane Doe", "jane@example.com", "456 Main St"), "Personal", 500.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        String result = accountService.deposit(1L, 200.0);

        assertEquals("Deposit successful!", result);
        assertEquals(700.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void withdraw_ShouldDecreaseBalance_WhenSufficientFunds() {
        Account account = new Account(new User("Jane Doe", "jane@example.com", "456 Main St"), "Personal", 1000.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        String result = accountService.withdraw(1L, 200.0);

        assertEquals("Withdrawal successful!", result);
        assertEquals(800.0, account.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void withdraw_ShouldReturnError_WhenInsufficientFunds() {
        Account account = new Account(new User("Jane Doe", "jane@example.com", "456 Main St"), "Personal", 100.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        String result = accountService.withdraw(1L, 200.0);

        assertEquals("Insufficient funds. Overdraft unavailable.", result);
        assertEquals(100.0, account.getBalance());
        verify(accountRepository, never()).save(account);
    }

    @Test
    void closeAccount_ShouldDeleteAccount_WhenBalanceIsZero() {
        Account account = new Account(new User("John Doe", "john@example.com", "123 Main St"), "Personal", 0.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        String result = accountService.closeAccount(1L);

        assertEquals("Account closed successfully.", result);
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    void closeAccount_ShouldReturnError_WhenBalanceIsNotZero() {
        Account account = new Account(new User("Jane Doe", "jane@example.com", "456 Main St"), "Personal", 100.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        String result = accountService.closeAccount(1L);

        assertEquals("Account balance must be zero to close. Please visit a branch.", result);
        verify(accountRepository, never()).delete(account);
    }

    @Test
    void transferBetweenAccounts_ShouldTransferFunds_WhenBothAccountsExistAndBelongToSameUser() {
        User user = new User("John Doe", "john@example.com", "123 Main St");
        Account fromAccount = new Account(user, "Personal", 1000.0);
        Account toAccount = new Account(user, "Savings", 500.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        String result = accountService.transferBetweenAccounts(1L, 2L, 300.0);

        assertEquals("Transfer successful!", result);
        assertEquals(700.0, fromAccount.getBalance());
        assertEquals(800.0, toAccount.getBalance());
        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
    }

    @Test
    void transferBetweenAccounts_ShouldReturnError_WhenAccountsBelongToDifferentUsers() {
        Account fromAccount = new Account(new User("John Doe", "john@example.com", "123 Main St"), "Personal", 1000.0);
        Account toAccount = new Account(new User("Jane Doe", "jane@example.com", "456 Main St"), "Savings", 500.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        String result = accountService.transferBetweenAccounts(1L, 2L, 300.0);

        assertEquals("Transfer failed: Accounts belong to different users.", result);
        verify(accountRepository, never()).save(fromAccount);
        verify(accountRepository, never()).save(toAccount);
    }
}
