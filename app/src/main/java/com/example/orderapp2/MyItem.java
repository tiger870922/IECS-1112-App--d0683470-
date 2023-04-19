package com.example.orderapp2;

public class MyItem {
    private int item_img;
    private String name;
    private float price;

    public MyItem(int item,String name, float price) {
        this.item_img = item;
        this.name = name;
        this.price = price;
    }

    public int getItem_img() {
        return item_img;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
