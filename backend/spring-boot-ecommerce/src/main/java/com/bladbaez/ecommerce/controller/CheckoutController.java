package com.bladbaez.ecommerce.controller;

import com.bladbaez.ecommerce.dto.Purchase;
import com.bladbaez.ecommerce.dto.PurchaseResponse;
import com.bladbaez.ecommerce.service.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);
        return purchaseResponse;
    }
}
