package com.exe201.project.exe_201_beestay_be.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "homestays")
public class Homestay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

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

    @Column(name = "review_count")
    private Integer reviewCount;

    @Column(name = "price_per_night")
    private Integer pricePerNight;

    @Column(name = "original_price_per_night")
    private Integer originalPricePerNight;

    @Column(name = "discount_percentage")
    private Integer discountPercentage;

    @Size(max = 255)
    @Column(name = "video_tour_url")
    private String videoTourUrl;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 50)
    @Column(name = "room_type", length = 50)
    private String roomType;

    @Column(name = "room_count")
    private Integer roomCount;

    @Column(name = "max_guests")
    private Integer maxGuests;

    @Column(name = "bed_count")
    private Integer bedCount;

    @Column(name = "bathroom_count")
    private Integer bathroomCount;

    @Column(name = "is_flash_sale")
    private Boolean isFlashSale;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "is_instant_book")
    private Boolean isInstantBook;

    @Column(name = "is_recommended")
    private Boolean isRecommended;

    @Column(name = "distance_to_center")
    private Double distanceToCenter;

}