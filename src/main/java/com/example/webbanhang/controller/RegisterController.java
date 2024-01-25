package com.example.webbanhang.controller;

import com.example.webbanhang.dao.RoleReponsitory;
import com.example.webbanhang.entity.Role;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.service.UserService;
import com.example.webbanhang.web.RegisterUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/register")
public class RegisterController {
    UserService userService;
    RoleReponsitory roleReponsitory;

    @Autowired
    public RegisterController(UserService userService,RoleReponsitory roleReponsitory) {
        this.userService = userService;
        this.roleReponsitory = roleReponsitory;
    }

    @GetMapping("/showRegisterForm")
    public String showRegisterForm(Model model) {
        RegisterUser ru = new RegisterUser();
        model.addAttribute("registerUser" , ru);
        return "register/form";
    }
    @InitBinder
    public void initBinder(WebDataBinder data){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        data.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @PostMapping("/process")
    public String process(@Valid @ModelAttribute("registerUser") RegisterUser registerUser,
                          BindingResult result,
                          Model model,
                          HttpSession session ) {
        String username = registerUser.getUsername();
        if(result.hasErrors()) {
            return "register/form";
        }
        // Kiểm tra user đã tồn tại
        User userEx = userService.findByUsername(username);
        if(userEx !=null) {
            model.addAttribute("registerUser" , new RegisterUser());
            model.addAttribute("myError","Tài khoản đã tồn tại");
            return "register/form";
        }
        // Nếu chưa tồn tại
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(bCrypt.encode(registerUser.getPassword()));
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setEmail(registerUser.getEmail());

        Role defaultRole = roleReponsitory.findByName("ROLE_USER");
        Collection<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);


        userService.save(user);

        // thông báo thành công
        session.setAttribute("myuser",user);
        return "register/confirmation";

    }
}
