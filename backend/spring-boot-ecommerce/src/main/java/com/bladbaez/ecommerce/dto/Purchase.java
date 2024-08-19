package com.bladbaez.ecommerce.dto;

import com.bladbaez.ecommerce.entity.Address;
import com.bladbaez.ecommerce.entity.Customer;
import com.bladbaez.ecommerce.entity.Order;
import com.bladbaez.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}
