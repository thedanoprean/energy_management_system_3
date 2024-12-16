package com.example.communicationmicroservice.services;


import com.example.communicationmicroservice.entities.mcDevice;
import com.example.communicationmicroservice.entities.mcSensor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class mcService {
    @RabbitListener(queues = "Device_Service_Queue")
    public void mcServiceQueue(mcDevice device){
        System.out.println(device.getDeviceId());
        System.out.println(device.getConsumption());
        System.out.println(device.getDeviceId());
    }


    private final com.example.communicationmicroservice.repositories.mcSensorRepository sensorRepository;

    @Autowired
    public mcService(com.example.communicationmicroservice.repositories.mcSensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    @RabbitListener(queues = "energy-queue")
    public void mcSensorQueue(mcSensor sensor){
        System.out.println(sensor.getMeasurement());
        System.out.println(sensor.getTimestamp());
        System.out.println(sensor.getDeviceId());
        System.out.println(sensor.getUserID());
        String randomSensorId = UUID.randomUUID().toString();
        sensor.setSensorId(randomSensorId);
        sensorRepository.save(sensor);
    }
}