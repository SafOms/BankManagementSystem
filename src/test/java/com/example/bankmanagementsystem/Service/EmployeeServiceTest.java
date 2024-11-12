package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Exception.ResourceNotFoundException;
import com.example.bankmanagementsystem.Exception.UnauthorizedAccessException;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private static final String VALID_HR_PIN = "1234";
    private static final String INVALID_HR_PIN = "0000";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployee_ShouldReturnSuccessMessage_WhenValidHrPin() {
        Employee employee = new Employee("Alice", "alice@example.com", "Manager", VALID_HR_PIN);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        String result = employeeService.createEmployee("Alice", "alice@example.com", "Manager", VALID_HR_PIN);

        assertTrue(result.contains("Employee created with ID: "));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_ShouldReturnErrorMessage_WhenInvalidHrPin() {
        String result = employeeService.createEmployee("Bob", "bob@example.com", "Clerk", INVALID_HR_PIN);

        assertEquals("Invalid HR PIN. Employee creation failed.", result);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void findEmployeeById_ShouldReturnEmployee_WhenEmployeeExists() {
        Employee employee = new Employee("John", "john@example.com", "Technician", VALID_HR_PIN);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> foundEmployee = employeeService.findEmployeeById(1L);

        assertTrue(foundEmployee.isPresent());
        assertEquals("John", foundEmployee.get().getName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void findEmployeeById_ShouldThrowException_WhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.findEmployeeById(1L));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void updateEmployee_ShouldUpdateEmployee_WhenEmployeeExists() {
        Employee existingEmployee = new Employee("Eve", "eve@example.com", "Analyst", VALID_HR_PIN);
        Employee updatedDetails = new Employee("Eve Updated", "eveupdated@example.com", "Senior Analyst", VALID_HR_PIN);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedDetails);

        Employee updatedEmployee = employeeService.updateEmployee(1L, "Eve Updated", "eveupdated@example.com", "Senior Analyst");

        assertNotNull(updatedEmployee);
        assertEquals("Eve Updated", updatedEmployee.getName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void updateEmployee_ShouldThrowException_WhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                employeeService.updateEmployee(1L, "Eve", "eve@example.com", "Analyst"));
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee_WhenValidHrPinAndEmployeeExists() {
        Employee employee = new Employee("Alice", "alice@example.com", "Manager", VALID_HR_PIN);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        String result = employeeService.deleteEmployee(1L, VALID_HR_PIN);

        assertEquals("Employee deleted successfully.", result);
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void deleteEmployee_ShouldThrowUnauthorizedAccessException_WhenInvalidHrPin() {
        assertThrows(UnauthorizedAccessException.class, () -> employeeService.deleteEmployee(1L, INVALID_HR_PIN));
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    void deleteEmployee_ShouldThrowResourceNotFoundException_WhenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(1L, VALID_HR_PIN));
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }
}
