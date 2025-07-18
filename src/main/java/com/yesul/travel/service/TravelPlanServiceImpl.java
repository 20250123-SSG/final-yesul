package com.yesul.travel.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yesul.exception.handler.UserNotFoundException;
import com.yesul.exception.handler.TravelPlanNotFoundException;
import com.yesul.travel.model.dto.TravelPlanDto;
import com.yesul.travel.model.entity.TravelPlan;
import com.yesul.travel.repository.TravelPlanRepository;
import com.yesul.user.repository.UserRepository;
import com.yesul.user.model.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanRepository travelPlanRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional(readOnly = true)
    public List<TravelPlanDto> getUserTravelPlans(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다. ID=" + userId));

        return travelPlanRepo.findByUserOrderByCreatedAtDesc(user).stream()
                .map(tp -> new TravelPlanDto(
                        tp.getId(),
                        tp.getUser().getId(),
                        tp.getTravelPlan(),
                        tp.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTravelPlan(Long planId, Long userId) {
        TravelPlan tp = travelPlanRepo.findById(planId)
                .orElseThrow(() -> new TravelPlanNotFoundException("여행 계획을 찾을 수 없습니다. ID=" + planId));

        if (!tp.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 계획만 삭제할 수 있습니다.");
        }
        travelPlanRepo.delete(tp);
    }
}
