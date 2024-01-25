package com.example.webbanhang.dao;

import com.example.webbanhang.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReponsitory extends JpaRepository<Product,Long> {
    List<Product> getByTag(String tag);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Product> searchProductsByNameContaining(String keyword);


}
