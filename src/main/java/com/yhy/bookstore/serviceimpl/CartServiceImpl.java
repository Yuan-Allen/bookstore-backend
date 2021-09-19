package com.yhy.bookstore.serviceimpl;

import com.yhy.bookstore.dao.CartDao;
import com.yhy.bookstore.entity.CartItem;
import com.yhy.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;

    @Override
    public CartItem getCartItemById(Integer cartId)
    {
        return cartDao.getCartItemById(cartId);
    }

    @Override
    public void addCartItem(CartItem cartItem)
    {
        cartDao.addCartItem(cartItem);
    }

    @Override
    public void delCartItem(Integer cartItemId)
    {
        cartDao.delCartItem(cartItemId);
    }
}
