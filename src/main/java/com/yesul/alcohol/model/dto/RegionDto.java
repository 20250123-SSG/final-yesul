package com.yesul.alcohol.model.dto;

public class RegionDto {
    private Long id;
    private String province;
    private String city;
    public RegionDto(Long id, String province, String city) {
        this.id = id; this.province = province; this.city = city;
    }
    // getters...
}