package com.example.webbanhang.dao;

import com.example.webbanhang.entity.Role;
import com.example.webbanhang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserReponsitory extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    List<User> findByRoles_Name(String roleName);
}
