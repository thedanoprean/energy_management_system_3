package com.example.energymanagement.controllers;

import com.example.energymanagement.dtos.UserDetailsDTO;
import com.example.energymanagement.entities.Role;
import com.example.energymanagement.entities.Users;
import com.example.energymanagement.login.LoginRequest;
import com.example.energymanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:7000")
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Users> getSingleUser(@PathVariable String userId) {
        Users user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Users>> getAllUser() {
        List<Users> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> save(@RequestBody UserDetailsDTO user) {
        return userService.save(user);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable("id") String userId, @Valid @RequestBody UserDetailsDTO updatedUserDTO) {
        userService.updateUser(userId, updatedUserDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> userLogin(@RequestBody LoginRequest userLoginRequest) {
        try {
            String username = userLoginRequest.getUsername();
            String password = userLoginRequest.getPassword();

            String userId = userService.authenticateUser(username, password);
            Users user = userService.getUser(userId);

            if (user.getRole() == Role.ADMIN) {
                // Authentication with admin role
                Map<String, String> response = new HashMap<>();
                response.put("userId", user.getId());
                response.put("role", Role.ADMIN.name()); // Add the role for the frontend

                return ResponseEntity.ok(response);
            } else {
                // Authentication with user role
                Map<String, String> response = new HashMap<>();
                response.put("userId", user.getId());
                response.put("role", Role.USER.name()); // Add the role for the frontend

                return ResponseEntity.ok(response);
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



}
