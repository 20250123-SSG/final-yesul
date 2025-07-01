package com.yesul.alcohol.model.entity;

import com.yesul.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "alcohol")
@NoArgsConstructor
public class Alcohol extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String brand;

    @Column(nullable = false, length = 255)
    private String type;

    @Column(name = "volume_ml", nullable = false)
    private Integer volumeMl;

    @Column(precision = 4, scale = 2, nullable = false)
    private BigDecimal abv;

    @Column(name = "ingredients", length = 1024)
    private String ingredients;  // 예: "사과, 오크칩, 이스트"

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "taste_description", columnDefinition = "TEXT")
    private String tasteDescription;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 500)
    private String image;

    @OneToMany(mappedBy = "alcohol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlcoholTaste> alcoholTastes = new ArrayList<>();

    @OneToMany(mappedBy = "alcohol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlcoholRegion> alcoholRegions = new ArrayList<>();
}