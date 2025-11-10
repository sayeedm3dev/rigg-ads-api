package com.rigg.ads.repository;

import com.rigg.ads.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByClientId(Long clientId);
//    List<Campaign> findByActiveStatus(Integer activeStatus);
    List<Campaign> findByActiveStatusOrderByIdDesc(Integer activeStatus);

    long countByActiveStatus(Integer activeStatus);

}

