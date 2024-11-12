package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Exception.InvalidInputException;
import com.example.bankmanagementsystem.Exception.ResourceNotFoundException;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service


public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
        if (user.getFullName() == null || user.getEmail() == null) {
            throw new InvalidInputException("User creation failed: Full name and email are required.");
        }
        return userRepository.save(user);
    }


    // Find a user by ID
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }



    // Get all users
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    // Update user details
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setFullName(userDetails.getFullName());
        user.setEmail(userDetails.getEmail());
        user.setAddress(userDetails.getAddress());
        return userRepository.save(user);
    }


    // Delete a user by ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
