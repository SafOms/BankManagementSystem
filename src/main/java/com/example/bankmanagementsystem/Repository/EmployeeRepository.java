package com.example.bankmanagementsystem.Repository;

import com.example.bankmanagementsystem.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//    Optional<Employee> findByEmail(String email);
}