package com.austin.simplifymoney.digital_gold_backend.repository;

import com.austin.simplifymoney.digital_gold_backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.goldPurchasedInGrams) FROM Transaction t WHERE t.userId = :userId")
    Double getTotalGoldByUser(Long userId);
}
