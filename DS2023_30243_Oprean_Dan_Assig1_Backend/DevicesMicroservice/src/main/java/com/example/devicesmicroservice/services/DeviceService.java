package com.example.devicesmicroservice.services;

import com.example.devicesmicroservice.controllers.handlers.exceptionsmodel.ResourceNotFoundException;
import com.example.devicesmicroservice.dtos.DeviceDetailsDTO;
import com.example.devicesmicroservice.dtos.builders.DeviceBuilder;
import com.example.devicesmicroservice.entities.Device;
import com.example.devicesmicroservice.entities.UserID;
import com.example.devicesmicroservice.repositories.DeviceRepository;
import com.example.devicesmicroservice.repositories.UserIDRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserIDRepository userIdRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserIDRepository userIdRepository) {
        this.deviceRepository = deviceRepository;
        this.userIdRepository = userIdRepository;

    }

    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }


    public DeviceDetailsDTO findDeviceById(String id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

    public Device create(Device device) {
        String randomDeviceId = UUID.randomUUID().toString();
        device.setId(randomDeviceId);
        return deviceRepository.save(device);
    }

    public Device update(String deviceId, Device updatedDevice) {
        Device existingDevice = deviceRepository.findByDeviceId(deviceId);
        if (existingDevice != null) {
            // Update the properties of the existing device with the values from the updatedDevice
            existingDevice.setDescription(updatedDevice.getDescription());
            existingDevice.setAddress(updatedDevice.getAddress());
            existingDevice.setConsumption(updatedDevice.getConsumption());
            // Save the updated device
            return deviceRepository.save(existingDevice);
        } else {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);
        }
    }


    public void delete(String deviceId) throws IOException {
        Device device = deviceRepository.findByDeviceId(deviceId);
        if (device != null) {
            deviceRepository.delete(device);
        } else {
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);
        }
    }

    public List<Device> getDeviceByUserId(UserID userId) {
        return deviceRepository.findByUserID(userId);
    }


}
