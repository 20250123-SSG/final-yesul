package com.yesul.alcohol.model.dto;

import java.util.List;

public class AlcoholDetailDto {
    private Long alcoholId;
    private String alcoholName;
    private List<FoodDto> foods;
    private List<RegionDto> regions;

    public AlcoholDetailDto(Long alcoholId, String alcoholName,
                            List<FoodDto> foods, List<RegionDto> regions) {
        this.alcoholId = alcoholId;
        this.alcoholName = alcoholName;
        this.foods = foods;
        this.regions = regions;
    }
}