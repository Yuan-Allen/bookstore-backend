package com.yhy.bookstore.utils.jmsutils.beans;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderBean {

  private int userId;

  private Timestamp time;

  private List<OrderItemBean> items;

  public OrderBean() {
    userId = 0;
    time = null;
    items = null;
  }

  public OrderBean(int userId, Timestamp time, List<OrderItemBean> items) {
    this.userId = userId;
    this.time = time;
    this.items = items;
  }
}
