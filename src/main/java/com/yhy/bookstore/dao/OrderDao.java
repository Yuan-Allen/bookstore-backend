package com.yhy.bookstore.dao;

import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.OrderItem;

import java.util.List;

public interface OrderDao {
  void addOrder(Order order);

  void addOrderItem(OrderItem orderItem);

  void addOrderItems(List<OrderItem> orderItems);

  void delOrder(Integer orderId);
}
