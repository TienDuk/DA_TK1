package com.example.webbanhang.dao;

import com.example.webbanhang.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReponsitory extends JpaRepository<Role, Long> {
    public Role findByName(String name);
}
