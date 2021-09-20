package com.yhy.bookstore.service;

import com.yhy.bookstore.entity.OrderItem;
import com.yhy.bookstore.utils.jmsutils.beans.OrderBean;

public interface OrderService {
  void addOrder(OrderBean orderBean);

  void addOrderItem(OrderItem orderItem);

  void delOrder(Integer orderId);
}
