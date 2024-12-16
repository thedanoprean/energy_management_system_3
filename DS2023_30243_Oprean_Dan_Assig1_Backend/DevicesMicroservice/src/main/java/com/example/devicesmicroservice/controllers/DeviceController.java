package com.example.devicesmicroservice.controllers;

import com.example.devicesmicroservice.config.JwtUtils;
import com.example.devicesmicroservice.config.RabbitMQConfig;
import com.example.devicesmicroservice.dtos.DeviceDetailsDTO;
import com.example.devicesmicroservice.entities.Device;
import com.example.devicesmicroservice.entities.UserID;
import com.example.devicesmicroservice.services.DeviceService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/device")
@CrossOrigin
public class DeviceController {

    @Autowired
    private final JwtUtils jwtUtils;

    @Autowired
    private final DeviceService deviceService;

    @Autowired
    private RabbitTemplate template;

    @Autowired
    public DeviceController(DeviceService deviceService, JwtUtils jwtUtils) {
        this.deviceService = deviceService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping
    public ResponseEntity<List<Device>> getDevices(@RequestHeader("Authorization") String token){
        if (jwtUtils.isValidToken(token) && jwtUtils.isAdmin(token) || jwtUtils.isUser(token)) {
            return ResponseEntity.ok(deviceService.getDevices());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Metodă pentru crearea unui dispozitiv
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> create(@RequestBody Device device, @RequestHeader("Authorization") String token) {
        if (jwtUtils.isValidToken(token) && (jwtUtils.isAdmin(token))) {
            Device createdDevice = deviceService.create(device);
            template.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEYS, createdDevice);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }



    // Metodă pentru obținerea detaliilor unui dispozitiv
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<DeviceDetailsDTO> getDevice(@PathVariable("id") String deviceId, @RequestHeader("Authorization") String token) {
        if (jwtUtils.isValidToken(token) && (jwtUtils.isAdmin(token))) {
            DeviceDetailsDTO dto = deviceService.findDeviceById(deviceId);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Metodă pentru obținerea dispozitivelor asociate unui utilizator
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Device>> getDevicesByUserId(@PathVariable UserID userId, @RequestHeader("Authorization") String token) {
        if (jwtUtils.isValidToken(token) && (jwtUtils.isAdmin(token) || jwtUtils.isUser(token))) {
            return ResponseEntity.ok(deviceService.getDeviceByUserId(userId));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    // Metodă pentru ștergerea unui dispozitiv
    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<Device> deleteDevice(@PathVariable String deviceId, @RequestHeader("Authorization") String token) throws IOException {
        if (jwtUtils.isValidToken(token) && (jwtUtils.isAdmin(token) || jwtUtils.isUser(token))) {
            deviceService.delete(deviceId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Metodă pentru actualizarea unui dispozitiv
    @PutMapping("/update/{deviceId}")
    public ResponseEntity<Device> updateDevice(@PathVariable String deviceId, @RequestBody Device updatedDevice, @RequestHeader("Authorization") String token) {
        if (jwtUtils.isValidToken(token) && (jwtUtils.isAdmin(token) || jwtUtils.isUser(token))) {
            Device updated = deviceService.update(deviceId, updatedDevice);
            if (updated != null) {
                return new ResponseEntity<>(updated, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
