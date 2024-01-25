package com.example.webbanhang.controller;

import com.example.webbanhang.dao.BillReponsitory;
import com.example.webbanhang.dao.CartReponsitory;
import com.example.webbanhang.dao.ProductReponsitory;
import com.example.webbanhang.dao.UserReponsitory;
import com.example.webbanhang.entity.Bill;
import com.example.webbanhang.entity.Cart;
import com.example.webbanhang.entity.Product;
import com.example.webbanhang.entity.Role;
import com.example.webbanhang.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class UserController {
    private CartReponsitory cartReponsitory;
    private ProductReponsitory productReponsitory;

    private BillReponsitory billReponsitory;

    private CartService cartService;

    private UserReponsitory userReponsitory;

    @Autowired
    public UserController(CartReponsitory cartReponsitory,
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

    @GetMapping("/add")
    public String addToCart(@RequestParam("id") long id,
                            Model model,
                            Principal principal) {

        Product product = productReponsitory.findById(id).get();
        Cart cart = new Cart();
        cart.setIdProduct(id);
        cart.setUserName(principal.getName());
        cart.setName(product.getName());
        cart.setPrice(product.getPrice());
        cart.setQuatity(1);
        cart.setTotal(cart.getPrice()*cart.getQuatity());
        cartReponsitory.save(cart);
        List<Cart> carts = cartReponsitory.findByUserName(principal.getName());
            double totalPrice = 0;
             for (Cart cartx : carts) {
                 totalPrice += cartx.getPrice()*cartx.getQuatity();
             }

             int dem = cartReponsitory.demSoLuongNguoiCoTenA(principal.getName());
        model.addAttribute("carts" , carts);
             model.addAttribute("totalPrice" , totalPrice);
             model.addAttribute("dem",dem);

        return "user/cart";

    }
    @PostMapping("/addmany")
    public String addManyToCart(@RequestParam("id") long id,
                                @RequestParam("number") int number,
                                Model model,
                                Principal principal) {
        if(number <= 0) {
            return "user/error";
        }
        Product product = productReponsitory.findById(id).get();
        Cart cart = new Cart();
        cart.setIdProduct(id);
        cart.setUserName(principal.getName());
        cart.setName(product.getName());
        cart.setPrice(product.getPrice());
        cart.setQuatity(number);
        cart.setTotal(cart.getPrice()*cart.getQuatity());
        cartReponsitory.save(cart);
        List<Cart> carts = cartReponsitory.findByUserName(principal.getName());
        double totalPrice = 0;
        for (Cart cartx : carts) {
            totalPrice += cartx.getPrice()*cartx.getQuatity();
        }
        int dem = cartReponsitory.demSoLuongNguoiCoTenA(principal.getName());
        model.addAttribute("carts" , carts);
        model.addAttribute("totalPrice" , totalPrice);
        model.addAttribute("dem",dem);

        return "redirect:/home/show?id="+id;
    }
    @GetMapping("/show")
    public String showCart(Model model,Principal principal) {
        List<Cart> carts = cartReponsitory.findByUserName(principal.getName());
            double totalPrice = 0;
             for (Cart cartx : carts) {
                 totalPrice += cartx.getPrice()*cartx.getQuatity();
             }

        int dem = cartReponsitory.demSoLuongNguoiCoTenA(principal.getName());
        model.addAttribute("carts" , carts);
             model.addAttribute("totalPrice" , totalPrice);
             model.addAttribute("dem" , dem);
             model.addAttribute("cartName" , principal.getName());
        return "user/cart";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") long id, Model model) {
        cartReponsitory.deleteById(id);
        return "redirect:/cart/show";
    }
    @GetMapping("/pay")
    public String pay(Model model,Principal principal) {
        String userName = principal.getName();
        List<Cart> carts = cartReponsitory.findByUserName(principal.getName());
        double totalPrice = 0;
        for (Cart cartx : carts) {
            totalPrice += cartx.getPrice()*cartx.getQuatity();
        }

        int dem = cartReponsitory.demSoLuongNguoiCoTenA(principal.getName());
        // Tạo ra 1 đối tượng bill mới
        Bill bill = new Bill();
        model.addAttribute("bill" , bill);

        model.addAttribute("carts" , carts);
        model.addAttribute("totalPrice" , totalPrice);
        model.addAttribute("dem" , dem);
        model.addAttribute("userName",userName);
        return "user/pay";
    }
    @PostMapping("/pay/confirm")
    public String payConfrim(@ModelAttribute("bill") Bill bill,
                             Model model,
                             Principal principal) {
        // Lấy ra thời gian
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        bill.setTime(formattedTime);

        //lấy ra tổng tiền hóa đơn
        List<Cart> carts = cartReponsitory.findByUserName(principal.getName());
        double totalPrice = 0;
        for (Cart cartx : carts) {
            totalPrice += cartx.getPrice()*cartx.getQuatity();
        }
        bill.setPrice(totalPrice);
        bill.setUserName(principal.getName());

        String detail = "";
        for ( Cart x:
             carts ) {
            detail += x.getIdProduct() + " : " + x.getName()+" : "+x.getPrice()+" só lượng: "+x.getQuatity()+" /// ";
        }
        detail += "Tổng thanh toán:" + totalPrice;
        bill.setDetail(detail);

        billReponsitory.save(bill);

        model.addAttribute("bill",bill);
        return "user/thankyou";
    }

    @GetMapping("/deleteAll")
    public String deleteAll(@RequestParam("name") String name) {
//       cartReponsitory.deleteCartsByName(principal.getName());

        cartService.deleteAll(name);
        return "redirect:/cart/show";
    }


    @GetMapping("/history")
    public String history(Model model, Principal principal) {
        List<Bill> bills = billReponsitory.findByUserName(principal.getName());



        model.addAttribute("bills", bills);
        return "user/history";
    }
    @GetMapping("/historydetail")
    public String historydetail(@RequestParam("id") long id, Model model) {
        Bill bill = billReponsitory.findById(id).get();
        model.addAttribute("bill", bill);
        return "user/historydetail";
    }



}
