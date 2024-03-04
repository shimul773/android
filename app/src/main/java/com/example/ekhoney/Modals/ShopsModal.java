package com.example.ekhoney.Modals;

public class ShopsModal {
    String id, title, img, cata;

    public ShopsModal(String id, String title, String img, String cata) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.cata = cata;
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
}
