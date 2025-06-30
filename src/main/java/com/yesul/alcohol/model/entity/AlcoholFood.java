package com.yesul.alcohol.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "alcohol_food")
public class AlcoholFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id", nullable = false)
    private Alcohol alcohol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

}