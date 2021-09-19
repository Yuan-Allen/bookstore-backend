package com.yhy.bookstore.repository;

import com.yhy.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where username = :username and password = :password")
    User checkUser(@Param("username") String username, @Param("password") String password);

    User getUserByUserId(Integer userId);

    List<User> findUserByUsername(String username);

    @Query("select u from User u")
    List<User> getUsers();
}
