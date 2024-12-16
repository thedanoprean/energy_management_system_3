package com.example.communicationmicroservice.repositories;


import com.example.communicationmicroservice.entities.mcSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface mcSensorRepository extends JpaRepository<mcSensor, Long> {
    // You can add custom query methods here if needed
}