package com.thoughtworks.letusgo.entity;

import java.util.List;

public class Item{
    private int id;
    private String barcode;
    private String name;
    private String unit;
    private double price;
    private Category category;
    private int discount;
    private List<Promotion> promotionList;

    public void setId(int id) {
        this.id = id;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}