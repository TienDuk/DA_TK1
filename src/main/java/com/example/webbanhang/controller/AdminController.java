package com.example.webbanhang.controller;

import com.example.webbanhang.dao.BillReponsitory;
import com.example.webbanhang.dao.CartReponsitory;
import com.example.webbanhang.dao.ProductReponsitory;
import com.example.webbanhang.dao.UserReponsitory;
import com.example.webbanhang.entity.Bill;
import com.example.webbanhang.entity.Product;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private CartReponsitory cartReponsitory;
    private ProductReponsitory productReponsitory;

    private BillReponsitory billReponsitory;

    private CartService cartService;

    private UserReponsitory userReponsitory;

    @Autowired
    public AdminController(CartReponsitory cartReponsitory,
                          ProductReponsitory productReponsitory,
                          BillReponsitory billReponsitory,
                          CartService cartService,
                           UserReponsitory userReponsitory) {
        this.cartReponsitory = cartReponsitory;
        this.productReponsitory = productReponsitory;
        this.billReponsitory = billReponsitory;
        this.cartService = cartService;
        this.userReponsitory = userReponsitory;
    }


    @GetMapping("/create") // Nhiệm vụ của cái này chỉ để hiển thị ra cái form
    public String create(Model model) {
        Product product = new Product();
        model.addAttribute("product" , product);
        return "admin/productform";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("product") Product product,
                        @RequestParam("id") long id) {
        productReponsitory.saveAndFlush(product);
    return "redirect:/home";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") long id,
                         Model model) {
        Product product = productReponsitory.findById(id).get();
        model.addAttribute(product);
        return "admin/productform";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        productReponsitory.deleteById(id);
        return "redirect:/home";
    }


    // Hiển thị danh sách người dùng
    @GetMapping("/bill")
    public String showAllBills(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Bill> bills = billReponsitory.findAll(PageRequest.of(page, 16));
        model.addAttribute("bills", bills);
        model.addAttribute("currentPage", page);



        return "admin/bills";
    }
    @GetMapping("/bill-detail")
    public String billDetail(@RequestParam("id") long id , Model model) {
        Bill bill = billReponsitory.findById(id).get();
        model.addAttribute("bill" , bill);
        return "admin/billDetail";
    }
    @GetMapping("/customer")
    public String myCustomer(@RequestParam("name") String name, Model model) {
        User us = userReponsitory.findByUsername(name);
        model.addAttribute("us",us);
        List<Bill> bill = billReponsitory.findByUserName(name);
        model.addAttribute("bill", bill);
        return "admin/customerDetail";
    }
    @PostMapping("/searchId")
    public String timKiemIdDonHang(@RequestParam long keyid , Model model) {
        Bill bill = billReponsitory.findById(keyid).get();
        model.addAttribute("bill" ,bill);
        return "admin/billDetail";
    }
    @PostMapping("/searchName")
    public String timKiemKhachHang(@RequestParam String keyword , Model model) {
        User us = userReponsitory.findByUsername(keyword);
        model.addAttribute("us" ,us);
        List<Bill> bill = billReponsitory.findByUserName(keyword);
        model.addAttribute("bill", bill);
        return "admin/customerDetail";
    }
}
