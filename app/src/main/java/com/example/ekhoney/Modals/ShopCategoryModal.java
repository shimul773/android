package com.example.ekhoney.Modals;

public class ShopCategoryModal {
    String id, title, products;

    public ShopCategoryModal(String id, String title, String products) {
        this.id = id;
        this.title = title;
        this.products = products;
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

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }
}
