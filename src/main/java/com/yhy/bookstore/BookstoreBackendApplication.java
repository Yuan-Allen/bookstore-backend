package com.yhy.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class BookstoreBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreBackendApplication.class, args);
  }
}
