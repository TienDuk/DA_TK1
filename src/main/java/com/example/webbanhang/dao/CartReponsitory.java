package com.example.webbanhang.dao;

import com.example.webbanhang.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartReponsitory extends JpaRepository<Cart, Long> {
    List<Cart> findByUserName(String name);

    @Query("SELECT COUNT(n) FROM Cart n WHERE n.userName = :ten")
    int demSoLuongNguoiCoTenA(@Param("ten") String ten);

    void deleteAllByUserName(String userName);
}
