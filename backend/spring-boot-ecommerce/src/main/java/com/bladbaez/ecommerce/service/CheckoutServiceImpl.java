package com.bladbaez.ecommerce.service;

import com.bladbaez.ecommerce.dao.CustomerRepository;
import com.bladbaez.ecommerce.dto.Purchase;
import com.bladbaez.ecommerce.dto.PurchaseResponse;
import com.bladbaez.ecommerce.entity.Customer;
import com.bladbaez.ecommerce.entity.Order;
import com.bladbaez.ecommerce.entity.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        // check if this is an existing customer
        String email = customer.getEmail();
        Customer customerFromDB = customerRepository.findByEmail(email);

        if(customerFromDB != null){
            // we found them... let's assign them accordingly
            customer = customerFromDB;
        }
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
