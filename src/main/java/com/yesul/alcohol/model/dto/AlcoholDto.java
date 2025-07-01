package com.yesul.alcohol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AlcoholDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal abv;

}