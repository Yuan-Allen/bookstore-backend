package com.yhy.bookstore.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "book description")
public class BookDescription {
  @Id private int id;

  @Field("description")
  private String descripton;

  public BookDescription(int id, String descripton) {
    this.id = id;
    this.descripton = descripton;
  }
}
