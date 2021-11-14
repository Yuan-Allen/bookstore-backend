package com.yhy.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableZuulProxy
@EnableNeo4jRepositories
@SpringBootApplication
public class BookstoreBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreBackendApplication.class, args);
  }
}
