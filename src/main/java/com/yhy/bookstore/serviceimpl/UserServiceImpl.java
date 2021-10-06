package com.yhy.bookstore.serviceimpl;

import com.yhy.bookstore.dao.UserDao;
import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.User;
import com.yhy.bookstore.service.UserService;
import com.yhy.bookstore.utils.visitsutils.Visits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserDao userDao;

  private Visits visits = new Visits();

  class VisitsAdder implements Runnable {
    public void run() {
      visits.increment();
    }
  }

  @Override
  public User checkUser(String username, String password) {
    return userDao.checkUser(username, password);
  }

  @Override
  public User getUserById(Integer userId) {
    return userDao.getUserById(userId);
  }

  @Override
  public List<Order> getOrders(Integer userId) {
    return userDao.getOrders(userId);
  }

  @Override
  public List<User> getUsers() {
    return userDao.getUsers();
  }

  @Override
  public void switchUserAuth(int userId) {
    userDao.switchUserAuth(userId);
  }

  @Override
  public void addUser(User user) {
    userDao.addUser(user);
  }

  @Override
  public boolean checkUsername(String username) {
    return userDao.checkUsername(username);
  }

  @Override
  public Integer visitsIncrement() {
    Thread t = new Thread(new VisitsAdder());
    t.start();
    try {
      t.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return visits.value();
  }
}
