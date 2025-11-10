package com.rigg.ads.repository;

import com.rigg.ads.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByActiveStatusOrderByIdDesc(Integer activeStatus);
    List<Client> findByIdAndActiveStatus(Long id, Integer activeStatus);
    long countByActiveStatus(Integer activeStatus);

}

