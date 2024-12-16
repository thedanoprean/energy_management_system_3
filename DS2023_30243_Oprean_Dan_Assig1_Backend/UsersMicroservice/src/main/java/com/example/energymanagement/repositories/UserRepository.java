package com.example.energymanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.energymanagement.entities.Users;

import java.util.Optional;


public interface UserRepository  extends JpaRepository<Users, String> {

    Users findByUsernameAndPassword(String username, String password);

    Optional<Users> findUserByUsername(String username);
}

