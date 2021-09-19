package com.yhy.bookstore.repository;

import com.yhy.bookstore.entity.Book;
import com.yhy.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem,Integer> {
    CartItem findCartItemByBookAndUserId(Book book, int userId);
}
