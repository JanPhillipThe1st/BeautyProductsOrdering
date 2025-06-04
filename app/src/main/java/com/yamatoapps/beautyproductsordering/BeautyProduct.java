package com.yamatoapps.beautyproductsordering;

public class BeautyProduct {
    public String name;
    public Double price;
    public int image;
    public String image_url;
    public String id = "";


    public BeautyProduct(String name, Double price, int image, String id) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.id = id;
    }
    public BeautyProduct(String name, Double price, String image_url, String id) {
        this.name = name;
        this.price = price;
        this.image_url = image_url;
        this.id = id;
    }
}
