package com.example.bankmanagementsystem.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity


public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_type") // Ensure this matches the column name
    private String accountType; // "Personal", "Business", "Savings"

    private LocalDate creationDate;
    private double balance;
    private double interestRate; // Applicable for Savings accounts only

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors
    public Account() {}

    public Account(User user, String accountType, double initialBalance) {
        this.user = user;
        this.accountType = accountType;
        this.creationDate = LocalDate.now();
        this.balance = initialBalance;

        if (accountType.equals("Savings")) {
            this.interestRate = 0.03; // Example interest rate for savings accounts
        } else {
            this.interestRate = 0.0;
        }
    }

    // Getters and Setters

    public Long getId() { return id; }

    public String getAccountType() { return accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType; }

    public LocalDate getCreationDate() { return creationDate; }

    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public double getInterestRate() { return interestRate; }

    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    // Apply interest to Savings account if balance is above a threshold
    public void applyInterest() {
        if ("Savings".equals(this.accountType) && this.balance > 1000) {
            this.balance += this.balance * this.interestRate;
        }
    }
}
