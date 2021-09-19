package com.yhy.bookstore.serviceimpl;

import com.yhy.bookstore.dao.OrderDao;
import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.OrderItem;
import com.yhy.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;

    @Override
    public void addOrder(Order order)
    {
        orderDao.addOrder(order);
    }

    @Override
    public void addOrderItem(OrderItem orderItem)
    {
        orderDao.addOrderItem(orderItem);
    }

    @Override
    public void delOrder(Integer orderId) { orderDao.delOrder(orderId); }
}
