package com.example.communicationmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "comm_devices")
public class mcDevice implements Serializable {
    @Id
    @Column(name= "id")
    private String deviceId;
    @ManyToOne()
    @JoinColumn(name = "userId")
    private mcUserId userID;
    @Column(name="description")
    private String description;
    @Column(name="address")
    private String address;
    @Column(name="consumption")
    private int consumption;
}
