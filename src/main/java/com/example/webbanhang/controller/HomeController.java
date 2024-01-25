package com.example.webbanhang.controller;

import com.example.webbanhang.dao.ProductReponsitory;
import com.example.webbanhang.dao.UserReponsitory;
import com.example.webbanhang.entity.Product;
import com.example.webbanhang.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private ProductReponsitory productReponsitory;
    private UserReponsitory userReponsitory;

    @Autowired
    public HomeController(ProductReponsitory productReponsitory,
                          UserReponsitory userReponsitory) {
        this.productReponsitory = productReponsitory;
        this.userReponsitory = userReponsitory;
    }


    //    @GetMapping("/")
//    public String showHomePage(Model model) {
//        return "home";
//    }
    @GetMapping("")
    public String getProductsPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> products = productReponsitory.findAll(PageRequest.of(page, 16));
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        return "home";
    }

    @PostMapping("/search")
    public String searchProductsByName(@RequestParam String keyword, Model model) {
        List<Product> searchResult = productReponsitory.searchProductsByNameContaining(keyword);
        model.addAttribute("searchResult", searchResult);
        return "searchResult";
    }
    @PostMapping("/searchId")
    public String searchProductsById( @RequestParam long keyid, Model model) {
        Product searchResult = productReponsitory.findById(keyid).get();
        model.addAttribute("product", searchResult);
        return "productDetail";
    }
    @GetMapping("/show")
    public String show(@RequestParam("id") long id, Model model) {
        Product product = productReponsitory.findById(id).get();
        model.addAttribute("product",product);
        return "productDetail";
    }
    @GetMapping("/sach")
    public String hienthi1(Model model) {
        List<Product> products1 = productReponsitory.getByTag("Sách");
        model.addAttribute("products1" , products1);
        return "home1";
    }
    @GetMapping("/do-dien-tu")
    public String hienthi2(Model model) {
        List<Product> products2 = productReponsitory.getByTag("Đồ điện tử");
        model.addAttribute("products2" , products2);
        return "home2";
    }
    @GetMapping("/thoi-trang")
    public String hienthi3(Model model) {
        List<Product> products3 = productReponsitory.getByTag("Thời trang");
        model.addAttribute("products3" , products3);
        return "home3";
    }
    @GetMapping("/khac")
    public String hienthi4(Model model) {
        List<Product> products4 = productReponsitory.getByTag("Khác");
        model.addAttribute("products4" , products4);
        return "home4";
    }

    @GetMapping("/info")
    public String myinfo(Model model, Principal principal) {
        User user = userReponsitory.findByUsername(principal.getName());
        model.addAttribute("my" , user);
        return "info";
    }

}
