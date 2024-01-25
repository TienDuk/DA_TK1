package com.example.webbanhang.controller;

import com.example.webbanhang.dao.ProductReponsitory;
import com.example.webbanhang.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductReponsitory productReponsitory;

    @Autowired
    public ProductController(ProductReponsitory productReponsitory) {
        this.productReponsitory = productReponsitory;
    }




    @PostMapping("/search")
    public String searchProductsByName(@RequestParam String keyword, Model model) {
        List<Product> searchResult = productReponsitory.searchProductsByNameContaining(keyword);
        model.addAttribute("searchResult", searchResult);
        return "myhome/searchResult";
    }
    @PostMapping("/searchId")
    public String searchProductsById( @RequestParam long keyid, Model model) {
        Product searchResult = productReponsitory.findById(keyid).get();
        model.addAttribute("product", searchResult);
        return "myhome/productDetail";
    }
    @GetMapping("/show")
    public String show(@RequestParam("id") long id, Model model) {
        Product product = productReponsitory.findById(id).get();
        model.addAttribute("product",product);
        return "myhome/productDetail";
    }






    //    @GetMapping("")
//    public String hienthi(Model model) {
//        List<Product> products =  productReponsitory.findAll();
//        model.addAttribute("products" , products);
//        return "myhome/home";
//    }



    @GetMapping("/sach")
    public String hienthi1(Model model) {
        List<Product> products1 = productReponsitory.getByTag("Sách");
        model.addAttribute("products1" , products1);
        return "myhome/home1";
    }
    @GetMapping("/do-dien-tu")
    public String hienthi2(Model model) {
        List<Product> products2 = productReponsitory.getByTag("Đồ điện tử");
        model.addAttribute("products2" , products2);
        return "myhome/home2";
    }
    @GetMapping("/thoi-trang")
    public String hienthi3(Model model) {
        List<Product> products3 = productReponsitory.getByTag("Thời trang");
        model.addAttribute("products3" , products3);
        return "myhome/home3";
    }
    @GetMapping("/khac")
    public String hienthi4(Model model) {
        List<Product> products4 = productReponsitory.getByTag("Khác");
        model.addAttribute("products4" , products4);
        return "myhome/home4";
    }

    @GetMapping("")
    public String getProductsPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Product> products = productReponsitory.findAll(PageRequest.of(page, 16));
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        return "myhome/home";
    }
}
