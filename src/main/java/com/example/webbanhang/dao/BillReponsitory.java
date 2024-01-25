package com.example.webbanhang.dao;

import com.example.webbanhang.entity.Bill;
import com.example.webbanhang.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillReponsitory extends JpaRepository<Bill,Long> {
    List<Bill> findByUserName(String name);
}
