package com.yhy.bookstore.service;

import com.yhy.bookstore.entity.CartItem;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface CartService {
    CartItem getCartItemById(Integer cartId);
    void addCartItem(CartItem cartItem);
    void delCartItem(Integer itemId);
}
