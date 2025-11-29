package com.austin.simplifymoney.digital_gold_backend.repository;

import com.austin.simplifymoney.digital_gold_backend.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    // Get the latest price entry
    Price findTopByOrderByIdDesc();
}
