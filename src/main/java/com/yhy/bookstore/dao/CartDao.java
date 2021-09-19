package com.yhy.bookstore.dao;

import com.yhy.bookstore.entity.CartItem;

public interface CartDao {
    CartItem getCartItemById(Integer cartId);
    void addCartItem(CartItem cartItem);
    void delCartItem(Integer cartItemId);
}
