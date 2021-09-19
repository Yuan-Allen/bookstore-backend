package com.yhy.bookstore.dao;

import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.User;

import java.util.List;

public interface UserDao {

    User checkUser(String username, String password);
    User getUserById(Integer userId);
    List<Order> getOrders(Integer userId);
    List<User> getUsers();

    void switchUserAuth(int userId);

    void addUser(User user);

    boolean checkUsername(String username);
}
