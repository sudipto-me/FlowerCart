package com.example.sudipto.hanselandgratel.model;

import android.graphics.Bitmap;

/**
 * Created by sudipto on 2/16/2017.
 */

public class Flowers {

    private int ProductID;
    private String Name;

    private String Category;
    private String Instructios;
    private double Price;
    private String Photo;
    private Bitmap bitmap;

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getInstructios() {
        return Instructios;
    }

    public void setInstructios(String instructios) {
        Instructios = instructios;
    }

    public double getPrice(double price) {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
