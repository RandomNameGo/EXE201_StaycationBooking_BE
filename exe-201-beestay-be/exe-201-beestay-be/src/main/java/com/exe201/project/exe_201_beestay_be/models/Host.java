package com.exe201.project.exe_201_beestay_be.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "hosts")
public class Host {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 50)
    @Column(name = "district", length = 50)
    private String district;

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "average_rating")
    private Float averageRating;

    @Column(name = "total_rooms")
    private Integer totalRooms;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @Column(name = "is_super_host")
    private Boolean isSuperHost;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Lob
    @Column(name = "bio")
    private String bio;

    @Size(max = 255)
    @Column(name = "facebook")
    private String facebook;

    @Size(max = 255)
    @Column(name = "instagram")
    private String instagram;

}