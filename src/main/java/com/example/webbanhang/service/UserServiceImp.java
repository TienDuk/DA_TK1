package com.example.webbanhang.service;

import com.example.webbanhang.dao.RoleReponsitory;
import com.example.webbanhang.dao.UserReponsitory;
import com.example.webbanhang.entity.Role;
import com.example.webbanhang.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService{
    private UserReponsitory userReponsitory;
    private RoleReponsitory roleReponsitory;

    @Autowired
    public UserServiceImp(UserReponsitory userReponsitory, RoleReponsitory roleReponsitory) {
        this.userReponsitory = userReponsitory;
        this.roleReponsitory = roleReponsitory;
    }

//    @PostConstruct
//    public void insertUser() {
//        User user1 = new User();
//        user1.setUsername("dat");
//        user1.setPassword("{bcrypt}$2a$12$ktXNVK/8McP8Hq/JJY3D4eBEGF202M2vsiVe.GDH5SApaj5DxlRvO");
//        user1.setEnabled(true);
//
//
//        Role role1 = new Role();
//        role1.setName("ROLE_ADMIN");
//
//        Collection<Role> roles = new ArrayList<>();
//        roles.add(role1);
//        user1.setRoles(roles);
//
//        userReponsitory.save(user1);
//    }

    @Override
    public User findByUsername(String username) {
        return userReponsitory.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReponsitory.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("Invalid Username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), rolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> rolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        userReponsitory.save(user);
    }
}
