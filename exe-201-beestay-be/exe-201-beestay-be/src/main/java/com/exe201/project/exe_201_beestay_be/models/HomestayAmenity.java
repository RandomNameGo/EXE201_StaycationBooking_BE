package com.exe201.project.exe_201_beestay_be.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "homestay_amenities")
public class HomestayAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "air_conditioner")
    private Boolean airConditioner;

    @Column(name = "kitchen")
    private Boolean kitchen;

    @Column(name = "private_bathroom")
    private Boolean privateBathroom;

    @Column(name = "pool")
    private Boolean pool;

    @Column(name = "pet_allowed")
    private Boolean petAllowed;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "balcony")
    private Boolean balcony;

    @Column(name = "bbq_area")
    private Boolean bbqArea;

    @Column(name = "room_service")
    private Boolean roomService;

    @Column(name = "security_camera")
    private Boolean securityCamera;

}