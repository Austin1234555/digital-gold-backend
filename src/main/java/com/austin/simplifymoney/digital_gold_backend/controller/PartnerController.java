package com.austin.simplifymoney.digital_gold_backend.controller;

import com.austin.simplifymoney.digital_gold_backend.model.PartnerAllotmentResponse;
import com.austin.simplifymoney.digital_gold_backend.model.PartnerPriceResponse;
import com.austin.simplifymoney.digital_gold_backend.service.PartnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @GetMapping("/get-price")
    public PartnerPriceResponse getPartnerPrice() {
        return partnerService.getCurrentPrice();
    }

    @PostMapping("/confirm-allotment")
    public PartnerAllotmentResponse confirmAllotment(@RequestParam double amount) {
        return partnerService.confirmAllotment(amount);
    }
}
