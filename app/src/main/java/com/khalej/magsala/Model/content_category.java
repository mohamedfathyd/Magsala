package com.khalej.magsala.Model;

import com.google.gson.annotations.SerializedName;

public class content_category {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("image")
    String image;
    @SerializedName("price")
    int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
