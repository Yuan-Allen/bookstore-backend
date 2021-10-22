package com.yhy.bookstore.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
  @Bean
  public SimpleFilter simpleFilter() {
    return new SimpleFilter();
  }
}
