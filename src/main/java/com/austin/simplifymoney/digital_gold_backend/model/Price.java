package com.austin.simplifymoney.digital_gold_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double goldPricePerGram;

    private LocalDateTime fetchedAt;
}
