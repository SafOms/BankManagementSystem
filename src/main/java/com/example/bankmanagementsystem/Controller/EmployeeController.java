package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    // Endpoint to create an employee
    @PostMapping("/create")
    public ResponseEntity<String> createEmployee(@RequestParam String name,
                                                 @RequestParam String email,
                                                 @RequestParam String position,
                                                 @RequestParam String hrPin) {
        String result = employeeService.createEmployee(name, email, position, hrPin);

        if (result.startsWith("Employee created")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result); // Return 401 for invalid HR PIN
        }
    }


    // Endpoint to get an employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employeeOpt = employeeService.findEmployeeById(id);
        return employeeOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint to get all employees
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Endpoint to update an employee's information
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long id,
                                                 @RequestParam String name,
                                                 @RequestParam String email,
                                                 @RequestParam String position) {
        Employee updatedEmployee = employeeService.updateEmployee(id, name, email, position);
        if (updatedEmployee != null) {
            return ResponseEntity.ok("Employee updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found.");
    }

    // Endpoint to delete an employee account securely using HR PIN
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id, @RequestParam String hrPin) {
        String result = employeeService.deleteEmployee(id, hrPin);
        return ResponseEntity.ok(result);
    }
}