package com.yhy.bookstore.serviceimpl;

import com.yhy.bookstore.dao.BookDao;
import com.yhy.bookstore.dao.OrderDao;
import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.OrderItem;
import com.yhy.bookstore.service.OrderService;
import com.yhy.bookstore.utils.jmsutils.beans.OrderBean;
import com.yhy.bookstore.utils.jmsutils.beans.OrderItemBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class OrderServiceImpl implements OrderService {
  @Autowired OrderDao orderDao;
  @Autowired BookDao bookDao;

  @Override
  @JmsListener(destination = "order")
  public void addOrder(OrderBean orderBean) {
    Order order = new Order();
    order.setUserId(orderBean.getUserId());
    order.setTime(orderBean.getTime());
    orderDao.addOrder(order);
    for (OrderItemBean orderItemBean : orderBean.getItems()) {
      Book book = bookDao.findOne(orderItemBean.getBookId());

      OrderItem orderItem = new OrderItem();
      orderItem.setBook(book);
      orderItem.setOrderId(order.getOrderId());
      orderItem.setBookNumber(orderItemBean.getBookNumber());
      orderItem.setBookPrice(orderItemBean.getBookPrice());
      orderDao.addOrderItem(orderItem);
    }
  }

  @Override
  public void addOrderItem(OrderItem orderItem) {
    orderDao.addOrderItem(orderItem);
  }

  @Override
  public void delOrder(Integer orderId) {
    orderDao.delOrder(orderId);
  }
}
