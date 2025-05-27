package com.exe201.project.exe_201_beestay_be.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "homestay_policies")
public class HomestayPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homestay_id")
    private Homestay homestay;

    @Column(name = "is_refundable")
    private Boolean isRefundable;

    @Column(name = "allow_pet")
    private Boolean allowPet;

    @Column(name = "allow_smoking")
    private Boolean allowSmoking;

}