package com.example.orderapp2;

public class BuyCarItem {

    private int item_img;
    private String name;
    private float price;
    private String note;
    private int amount;

    public BuyCarItem(int item,String name,String note,int amount, float price) {
        this.item_img = item;
        this.name = name;
        this.note = note;
        this.amount = amount;
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

    public String getNote() {
        return note;
    }

    public int getAmount() {
        return amount;
    }
}
