package com.yamatoapps.beautyproductsordering;

import java.util.Date;

public class Order {
    public String name;
    public Double price;
    public Date date_ordered;
    public String image_url;
    public String id = "";


    public Order(String name, Double price, String image_url, String id,Date date_ordered) {
        this.name = name;
        this.price = price;
        this.image_url = image_url;
        this.date_ordered = date_ordered;
        this.id = id;
    }
}
