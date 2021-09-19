package com.yhy.bookstore.daoimpl;

import com.yhy.bookstore.dao.UserDao;
import com.yhy.bookstore.entity.Order;
import com.yhy.bookstore.entity.User;
import com.yhy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String username, String password){

        return userRepository.checkUser(username,password);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public List<Order> getOrders(Integer userId)
    {
        User user = userRepository.getUserByUserId(userId);
        return user.getOrders();
    }

    @Override
    public List<User> getUsers()
    {
        List<User> users = userRepository.getUsers();
        List<User> ret = new ArrayList<>();
        for (User user:users) {
            if(user.getUserType()!=0)   //只返回普通用户，不返回管理员
                ret.add(user);
        }
        return ret;
    }

    @Override
    public void switchUserAuth(int userId)
    {
        User user = userRepository.getUserByUserId(userId);
        if(user.getUserType()==1)
            user.setUserType(-1);
        else if(user.getUserType()==-1)
            user.setUserType(1);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void addUser(User user)
    {
        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean checkUsername(String username)
    {
        return userRepository.findUserByUsername(username).size()==0;
    }
}
