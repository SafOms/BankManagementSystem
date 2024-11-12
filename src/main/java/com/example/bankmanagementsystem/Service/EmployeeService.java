package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Exception.ResourceNotFoundException;
import com.example.bankmanagementsystem.Exception.UnauthorizedAccessException;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service


public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${hr.pin}")
    private String masterHrPin;

    // Method to create an employee after HR PIN validation
    public String createEmployee(String name, String email, String position, String hrPin) {
        // Validate provided HR PIN against the master HR PIN
        if (!hrPin.equals(masterHrPin)) {
            return "Invalid HR PIN. Employee creation failed.";
        }

        // Create employee if HR PIN is valid
        Employee employee = new Employee(name, email, position, hrPin);
        employeeRepository.save(employee);
        return "Employee created with ID: " + employee.getId();
    }

    // Method to find employee by ID
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Method to get all employees
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    // Method to update employee details
    public Employee updateEmployee(Long id, String name, String email, String position) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        employee.setName(name);
        employee.setEmail(email);
        employee.setPosition(position);
        return employeeRepository.save(employee);
    }

    // Method to delete an employee after validating the HR PIN
    public String deleteEmployee(Long id, String providedHrPin) {
        // Validate provided HR PIN against the master HR PIN
        if (!providedHrPin.equals(masterHrPin)) {
            throw new UnauthorizedAccessException("Invalid HR PIN.");
        }

        // Find and delete the employee if they exist
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        employeeRepository.delete(employee);
        return "Employee deleted successfully.";
    }
}