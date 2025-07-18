package com.yesul.travel.service;

import java.util.List;

import com.yesul.travel.model.dto.TravelPlanDto;

public interface TravelPlanService {
    List<TravelPlanDto> getUserTravelPlans(Long userId);
    void deleteTravelPlan(Long planId, Long userId);
}
