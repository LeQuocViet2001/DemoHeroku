package com.example.demo.entity;

public class Item {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    Integer id;

    public Item( Integer id, String name) {
        this.name = name;
        this.id = id;
    }
}
