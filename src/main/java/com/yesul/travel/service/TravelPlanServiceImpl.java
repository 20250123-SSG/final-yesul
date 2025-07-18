package com.yesul.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import com.yesul.exception.handler.TravelPlanNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesul.travel.model.dto.TravelPlanDto;
import com.yesul.travel.model.entity.TravelPlan;
import com.yesul.travel.repository.TravelPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanRepository repo;

    @Override
    public List<TravelPlanDto> listUserPlans(Long userId) {
        return repo.findAllByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TravelPlanDto getUserPlan(Long userId, Long planId) {
        TravelPlan plan = repo.findByIdAndUserId(planId, userId)
                .orElseThrow(() -> new TravelPlanNotFoundException(
                        "사용자 " + userId + "의 여행계획 ID=" + planId + "를 찾을 수 없습니다."));
        return toDto(plan);
    }

    @Override
    @Transactional
    public void deleteUserPlan(Long userId, Long planId) {
        TravelPlan plan = repo.findByIdAndUserId(planId, userId)
                .orElseThrow(() -> new TravelPlanNotFoundException(
                        "사용자 " + userId + "의 여행계획 ID=" + planId + "를 찾을 수 없습니다."));
        repo.delete(plan);
    }

    private TravelPlanDto toDto(TravelPlan tp) {
        return new TravelPlanDto(
                tp.getId(),
                tp.getUser().getId(),
                tp.getTravelPlan(),
                tp.getCreatedAt()
        );
    }
}
