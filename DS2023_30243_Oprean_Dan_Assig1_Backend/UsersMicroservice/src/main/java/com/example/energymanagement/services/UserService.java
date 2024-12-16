package com.example.energymanagement.services;

import com.example.energymanagement.controllers.handlers.exceptionsmodel.ResourceNotFoundException;
import com.example.energymanagement.dtos.UserDetailsDTO;
import com.example.energymanagement.entities.Role;
import com.example.energymanagement.entities.Users;
import com.example.energymanagement.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<?> save(UserDetailsDTO user) {
        Optional<Users> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            Users existentUser = optionalUser.get();
            if (!user.getPassword().equals("")) {
                existentUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            existentUser.setRole(user.getRole());
            existentUser.setUsername(user.getUsername());
            userRepository.save(existentUser);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXJpYW4iLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdfQ.m_8TcWhHFFUTgldQ7iewOYXYXNpUFYywzkEYVzPntsx7mLQOqV5Czw0yt7lES-rRg8mdOD_uV4VwPt7ibDl27w");
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            Users newUser = new Users(user.getName(), user.getUsername(), passwordEncoder.encode(user.getPassword()), Role.USER);
            return ResponseEntity.ok(userRepository.save(newUser));
        }
    }

    public Users getUser(String userId) {
        //get user from database with the help of user repository
        Users user =  userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found" + userId));
        return user;
    }

    public ResponseEntity<?> getUserByUsername(String username) {
        Optional<Users> user = userRepository.findUserByUsername(username);
        if(user.isPresent()) {
            Users currentUser = user.get();
            return ResponseEntity.ok().body(currentUser);
        }
        return ResponseEntity.notFound().build();
    }

    public List<Users> getAllUser() {

        List<Users> users = userRepository.findAll();
        return users;
    }

    public void updateUser(String userId, UserDetailsDTO updatedUserDTO) {
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Users.class.getSimpleName() + " with id: " + userId));

        // Update the existing user with data from updatedUserDTO
        existingUser.setName(updatedUserDTO.getName());
        existingUser.setPassword(updatedUserDTO.getPassword());
        existingUser.setRole(updatedUserDTO.getRole());
        existingUser.setUsername(updatedUserDTO.getUsername());

        userRepository.save(existingUser);
        LOGGER.debug("User with id {} was updated in db", userId);
    }

    public void deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            // Șterge id-ul utilizatorului din tabela "UserID" a microserviciului de Device
            restTemplate.exchange("http://devicesmicroservice-application-deviceservice:8087/device/user2/delete/" + userId, HttpMethod.DELETE, null, String.class);

            userRepository.deleteById(userId);
            LOGGER.debug("User with id {} was deleted from db", userId);
        } else {
            LOGGER.error("User with id {} was not found in db", userId);
            throw new ResourceNotFoundException(Users.class.getSimpleName() + " with id: " + userId);
        }
    }

    public String authenticateUser(String username, String password) throws AuthenticationException {
        Users user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        }
        return user.getId();
    }
}
