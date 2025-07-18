package com.yesul.travel.repository;

import java.util.List;
import com.yesul.travel.model.entity.TravelPlan;
import com.yesul.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long>, JpaSpecificationExecutor<TravelPlan> {
    List<TravelPlan> findByUserOrderByCreatedAtDesc(User user);
}