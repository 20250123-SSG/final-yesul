package com.yesul.alcohol.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlcoholDto {

    private Long id;
    private String province;
    private String city;
    private String name;
    private String brand;
    private String type;
    private Integer volumeMl;
    private BigDecimal abv;
    private String ingredients;
    private String description;
    private String tasteDescription;

    private Integer price;
    private String pairingFoods;
    private String image;
}