package com.yhy.bookstore.utils.jmsutils.beans;

import lombok.Data;

@Data
public class OrderItemBean {
  private int bookId;
  private int bookNumber;
  private int bookPrice;

  public OrderItemBean() {
    this.bookId = 0;
    this.bookNumber = 0;
    this.bookPrice = 0;
  }

  public OrderItemBean(int bookId, int bookNumber, int bookPrice) {
    this.bookId = bookId;
    this.bookNumber = bookNumber;
    this.bookPrice = bookPrice;
  }
}
