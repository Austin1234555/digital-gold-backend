package com.austin.simplifymoney.digital_gold_backend.service;

import com.austin.simplifymoney.digital_gold_backend.model.Price;
import com.austin.simplifymoney.digital_gold_backend.model.Transaction;
import com.austin.simplifymoney.digital_gold_backend.repository.PriceRepository;
import com.austin.simplifymoney.digital_gold_backend.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final TransactionRepository transactionRepository;
    private final Random random = new Random();

    public PriceService(PriceRepository priceRepository,
                        TransactionRepository transactionRepository) {
        this.priceRepository = priceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void fetchAndSaveGoldPrice() {
        double price = 5000 + random.nextDouble() * 200;
        Price priceEntry = new Price();
        priceEntry.setGoldPricePerGram(price);
        priceEntry.setFetchedAt(LocalDateTime.now());
        priceRepository.save(priceEntry);
        System.out.println("Gold price saved: ₹" + price);
    }

    // 🔥 BUY GOLD
    public Transaction purchaseGold(Long userId, double money) {
        Price latestPrice = priceRepository.findTopByOrderByIdDesc();
        double gold = money / latestPrice.getGoldPricePerGram();

        Transaction tx = new Transaction();
        tx.setUserId(userId);
        tx.setType("BUY");
        tx.setAmountPaid(money);           // user pays this much
        tx.setGoldPurchasedInGrams(gold);  // +ve grams
        tx.setPurchasedAt(LocalDateTime.now());

        return transactionRepository.save(tx);
    }

    // 🔥 SELL GOLD  (Option A: block oversell)
    public Transaction sellGold(Long userId, double gramsToSell) {
        if (gramsToSell <= 0) {
            throw new IllegalArgumentException("Grams to sell must be positive");
        }

        // 1. Check wallet balance
        Double currentBalance = getWalletBalance(userId);
        if (currentBalance < gramsToSell) {
            throw new IllegalArgumentException(
                    "Not enough gold in wallet. You have only " + currentBalance + " grams");
        }

        // 2. Get latest price
        Price latestPrice = priceRepository.findTopByOrderByIdDesc();
        double moneyToGive = gramsToSell * latestPrice.getGoldPricePerGram();

        // 3. Save SELL transaction: gold negative so wallet reduces
        Transaction tx = new Transaction();
        tx.setUserId(userId);
        tx.setType("SELL");
        tx.setAmountPaid(moneyToGive);          // money user receives
        tx.setGoldPurchasedInGrams(-gramsToSell); // -ve grams
        tx.setPurchasedAt(LocalDateTime.now());

        return transactionRepository.save(tx);
    }

    // 🔥 WALLET BALANCE
    public Double getWalletBalance(Long userId) {
        Double total = transactionRepository.getTotalGoldByUser(userId);
        return total != null ? total : 0.0;
    }

    // 🔥 LATEST PRICE
    public Price getLatestPrice() {
        return priceRepository.findTopByOrderByIdDesc();
    }
}
