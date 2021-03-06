package com.yhy.bookstore.daoimpl;

import com.yhy.bookstore.dao.OrderDao;
import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.OrderItem;
import com.yhy.bookstore.repository.OrderItemRepository;
import com.yhy.bookstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
  @Autowired OrderItemRepository orderItemRepository;
  @Autowired OrderRepository orderRepository;

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void addOrder(Order order) {
    orderRepository.saveAndFlush(order);
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public void addOrderItem(OrderItem orderItem) {
    orderItemRepository.saveAndFlush(orderItem);
  }

  @Override
  public void addOrderItems(List<OrderItem> orderItems) {
    for (OrderItem item : orderItems) {
      orderItemRepository.saveAndFlush(item);
    }
  }

  @Override
  public void delOrder(Integer orderId) {
    if (orderRepository.existsById(orderId)) orderRepository.deleteById(orderId);
  }
}
