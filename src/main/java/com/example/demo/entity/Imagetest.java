package com.example.demo.entity;


public class Imagetest {

    private String image;
    private String namefoto;
    private String type ;

    public Imagetest(String image, String namefoto, String type) {
        this.image = image;
        this.namefoto = namefoto;
        this.type = type;
    }



    public Imagetest() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNamefoto() {
        return namefoto;
    }

    public void setNamefoto(String namefoto) {
        this.namefoto = namefoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
