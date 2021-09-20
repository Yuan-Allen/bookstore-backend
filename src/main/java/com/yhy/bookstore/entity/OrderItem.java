package com.yhy.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_item")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class OrderItem {

  @Id
  @Column(name = "item_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int itemId;

  @Column(name = "order_id")
  private int orderId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "book_id")
  private Book book;

  @Column(name = "book_number")
  private int bookNumber;

  private int bookPrice;
}
