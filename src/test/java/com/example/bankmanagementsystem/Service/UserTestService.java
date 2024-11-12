package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Exception.ResourceNotFoundException;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;


class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test user creation
    @Test
    void createUser_ShouldReturnCreatedUser() {
        User user = new User("John Doe", "johndoe@example.com", "123 Main St");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getFullName());
        verify(userRepository, times(1)).save(user);
    }

    // Test finding a user by ID when the user exists
    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        User user = new User("Jane Doe", "janedoe@example.com", "456 Main St");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));  // Wrap in Optional.of(...)

        User foundUser = userService.findById(1L);  // Now expecting User directly

        assertNotNull(foundUser);
        assertEquals("Jane Doe", foundUser.getFullName());
        verify(userRepository, times(1)).findById(1L);
    }

    // Test finding a user by ID when the user does not exist
    @Test
    void findById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());  // Return Optional.empty()

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(2L));

        verify(userRepository, times(1)).findById(2L);
    }

    // Test updating a user
    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        User existingUser = new User("Jane Doe", "janedoe@example.com", "456 Main St");
        User updatedDetails = new User("Jane Smith", "janesmith@example.com", "789 Main St");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedDetails);

        User updatedUser = userService.updateUser(1L, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("Jane Smith", updatedUser.getFullName());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(existingUser);
    }

    // Test deleting a user
    @Test
    void deleteUser_ShouldReturnTrue_WhenUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean isDeleted = userService.deleteUser(1L);

        assertTrue(isDeleted);
        verify(userRepository, times(1)).deleteById(1L);
    }

    // Test deleting a user when the user does not exist
    @Test
    void deleteUser_ShouldReturnFalse_WhenUserDoesNotExist() {
        when(userRepository.existsById(2L)).thenReturn(false);

        boolean isDeleted = userService.deleteUser(2L);

        assertFalse(isDeleted);
        verify(userRepository, never()).deleteById(anyLong());
    }
}