package com.yhy.bookstore.daoimpl;

import com.yhy.bookstore.dao.CartDao;
import com.yhy.bookstore.entity.CartItem;
import com.yhy.bookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

@Repository
public class CartDaoImpl implements CartDao {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartItem getCartItemById(Integer cartId) {
        return cartRepository.getOne(cartId);
    }

    @Override
    public void addCartItem(CartItem cartItem)
    {
        CartItem item = cartRepository.findCartItemByBookAndUserId(cartItem.getBook(), cartItem.getUserId());
        if(item!=null) {
            item.setBookNumber(cartItem.getBookNumber()+item.getBookNumber());
            cartRepository.saveAndFlush(item);
        }
        else {
            cartRepository.saveAndFlush(cartItem);
        }
    }

    @Override
    public  void delCartItem(Integer cartItemId)
    {
        if(cartRepository.existsById(cartItemId))
            cartRepository.deleteById(cartItemId);
    }
}
