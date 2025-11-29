package com.austin.simplifymoney.digital_gold_backend.repository;

import com.austin.simplifymoney.digital_gold_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
