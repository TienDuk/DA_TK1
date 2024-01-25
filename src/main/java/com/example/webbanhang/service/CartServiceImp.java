package com.example.webbanhang.service;

import com.example.webbanhang.dao.CartReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImp implements CartService{

    private CartReponsitory cartReponsitory;

    @Autowired
    public CartServiceImp(CartReponsitory cartReponsitory) {
        this.cartReponsitory = cartReponsitory;
    }

    @Override
    @Transactional
    public void deleteAll(String username) {
        cartReponsitory.deleteAllByUserName(username);
    }
}
