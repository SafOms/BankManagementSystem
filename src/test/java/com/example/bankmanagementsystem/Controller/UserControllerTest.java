//package com.example.bankmanagementsystem.Controller;
//
//import com.example.bankmanagementsystem.Model.User;
//import com.example.bankmanagementsystem.Service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Optional;
//
//import org.mockito.Mockito;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
// Main class defining the primary functionality.
// Main class defining the primary functionality.

//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    private User user;
//
//    @BeforeEach
//    public void setup() {
//        user = new User("John Doe", "john@example.com", "123 Main St");
//        user.setId(1L);  // Set the ID here
//    }
//
//    @Test
// Method to handle creation operations.
// Method to handle creation operations.

//    public void createUser_ShouldReturnCreatedUser() throws Exception {
//        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);
//
//        mockMvc.perform(post("/api/users/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"fullName\": \"John Doe\", \"email\": \"john@example.com\", \"address\": \"123 Main St\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(content().string("User created with ID: " + user.getId()));
//    }
//
//    @Test
//    public void getUserById_ShouldReturnUser_WhenUserExists() throws Exception {
//        Mockito.when(userService.findById(1L)).thenReturn(Optional.of(user));
//
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.fullName").value("John Doe"));
//    }
//
//    @Test
//    public void getUserById_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
//        Mockito.when(userService.findById(1L)).thenReturn(Optional.empty());
//
//        mockMvc.perform(get("/api/users/1"))
//                .andExpect(status().isNotFound());
//    }
//}