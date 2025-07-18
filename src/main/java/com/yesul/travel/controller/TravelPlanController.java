package com.yesul.travel.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yesul.travel.model.dto.TravelPlanDto;
import com.yesul.travel.service.TravelPlanService;
import com.yesul.user.service.PrincipalDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "여행계획", description = "사용자 여행계획 목록 조회, 상세 조회, 삭제")
@Controller
@RequestMapping("/travel-plans")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService service;

    @Operation(summary = "여행계획 목록 페이지", description = "로그인한 사용자가 생성한 모든 여행계획을 목록으로 보여줍니다.")
    @GetMapping
    public String listPlans(
            @AuthenticationPrincipal PrincipalDetails principal,
            Model model) {

        Long userId = principal.getUser().getId();
        List<TravelPlanDto> plans = service.listUserPlans(userId);
        model.addAttribute("travelPlans", plans);
        return "user/user-plan-list";
    }

    @Operation(summary = "여행계획 상세 페이지", description = "로그인한 사용자의 특정 여행계획 상세 정보를 보여줍니다.")
    @GetMapping("/{planId}")
    public String getPlan(
            @AuthenticationPrincipal PrincipalDetails principal,
            @PathVariable Long planId,
            Model model) {

        Long userId = principal.getUser().getId();
        TravelPlanDto plan = service.getUserPlan(userId, planId);
        model.addAttribute("plan", plan);
        return "user/user-plan-detail";
    }

    @Operation(summary = "여행계획 삭제 처리", description = "로그인한 사용자의 특정 여행계획을 삭제하고 목록으로 리디렉트합니다.")
    @PostMapping("/{planId}/delete")
    public String deletePlan(
            @AuthenticationPrincipal PrincipalDetails principal,
            @PathVariable Long planId,
            RedirectAttributes ra) {

        Long userId = principal.getUser().getId();
        service.deleteUserPlan(userId, planId);
        ra.addFlashAttribute("message", "여행계획이 삭제되었습니다.");
        return "redirect:/travel-plans";
    }
}
