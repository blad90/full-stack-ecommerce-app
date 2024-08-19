package com.bladbaez.ecommerce.service;

import com.bladbaez.ecommerce.dto.Purchase;
import com.bladbaez.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
