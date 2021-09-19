package com.yhy.bookstore.service;

import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.OrderItem;

public interface OrderService {
    void addOrder(Order order);
    void addOrderItem(OrderItem orderItem);
    void delOrder(Integer orderId);
}
