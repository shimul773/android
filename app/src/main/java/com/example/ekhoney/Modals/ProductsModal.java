package com.example.ekhoney.Modals;

public class ProductsModal {
    private String id, title, price, mrp, img, cata, catb, catc, brand, desc, cart;

    public ProductsModal(String id, String title, String price, String mrp, String img, String cata, String catb, String catc, String brand, String desc, String cart) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.mrp = mrp;
        this.img = img;
        this.cata = cata;
        this.catb = catb;
        this.catc = catc;
        this.brand = brand;
        this.desc = desc;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCata() {
        return cata;
    }

    public void setCata(String cata) {
        this.cata = cata;
    }

    public String getCatb() {
        return catb;
    }

    public void setCatb(String catb) {
        this.catb = catb;
    }

    public String getCatc() {
        return catc;
    }

    public void setCatc(String catc) {
        this.catc = catc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }
}
