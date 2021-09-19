package com.yhy.bookstore.dao;

import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.OrderItem;

public interface OrderDao {
    void addOrder(Order order);
    void addOrderItem(OrderItem orderItem);
    void delOrder(Integer orderId);
}
