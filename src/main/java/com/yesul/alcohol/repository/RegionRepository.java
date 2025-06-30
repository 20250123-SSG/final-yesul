package com.yesul.alcohol.repository;

import com.yesul.alcohol.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByProvinceAndCity(String province, String city);
    List<Region> findAllByProvince(String province);
    boolean existsByProvinceAndCity(String province, String city);
}