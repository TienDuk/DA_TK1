package com.example.webbanhang.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "id_product")
    private long idProduct;

    @Column(name="user_name")
    private String userName;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private int quatity;


    @Column(name = "total")
    private Double total;
    public Cart() {
    }

    public Cart(long id, long idProduct, String userName, String name, Double price, int quatity) {
        this.id = id;
        this.idProduct = idProduct;
        this.userName = userName;
        this.name = name;
        this.price = price;
        this.quatity = quatity;
    }

    public Cart(long idProduct, String userName, String name, Double price, int quatity) {
        this.idProduct = idProduct;
        this.userName = userName;
        this.name = name;
        this.price = price;
        this.quatity = quatity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
