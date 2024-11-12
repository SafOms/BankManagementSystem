package com.example.bankmanagementsystem.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity


public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String position;
    private String hrPin; // Used for restricted actions, such as creating or deleting other employees
    private LocalDate creationDate;

    // Constructors
    public Employee() {}

    public Employee(String name, String email, String position, String hrPin) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.hrPin = hrPin;
        this.creationDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getHrPin() { return hrPin; }
    public void setHrPin(String hrPin) { this.hrPin = hrPin; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
}