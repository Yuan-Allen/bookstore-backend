package com.yhy.bookstore.utils.visitsutils;

import java.util.concurrent.atomic.AtomicInteger;

public class Visits {
  private AtomicInteger count = new AtomicInteger(0);

  public Integer increment() {
    return count.incrementAndGet();
  }

  public Integer decrement() {
    return count.decrementAndGet();
  }

  public Integer value() {
    return count.get();
  }
}
