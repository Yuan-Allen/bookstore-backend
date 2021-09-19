package com.yhy.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;
    private String password;
    private Integer userType;
    private String email;
    private String address;

    @OneToMany()
    @JoinColumn(name="user_id")
    private List<CartItem> cart;

    @OneToMany()
    @JoinColumn(name="user_id")
    private List<Order> orders;
}
