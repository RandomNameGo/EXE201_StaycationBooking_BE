package com.exe201.project.exe_201_beestay_be.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(max = 100)
    @Column(name = "street", length = 100)
    private String street;

    @Size(max = 50)
    @Column(name = "district", length = 50)
    private String district;

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Size(max = 50)
    @Column(name = "province", length = 50)
    private String province;

    @Column(name = "joined_date")
    private LocalDate joinedDate;

    @Column(name = "current_booking")
    private Integer currentBooking;

    @Column(name = "total_booking_success")
    private Integer totalBookingSuccess;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Size(max = 20)
    @Column(name = "provider", length = 20)
    private String provider;

    @Size(max = 255)
    @Column(name = "provider_id")
    private String providerId;

    @Size(max = 255)
    @Column(name = "password")
    private String password;


    @Size(max = 255)
    @Column(name = "image")
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_id")
    private Account account;

}