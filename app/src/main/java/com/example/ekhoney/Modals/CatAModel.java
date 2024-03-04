package com.example.ekhoney.Modals;

public class CatAModel {


    String id, title, img, img2;

    public CatAModel(String id, String title, String img, String img2) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.img2 = img2;
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

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }
}
