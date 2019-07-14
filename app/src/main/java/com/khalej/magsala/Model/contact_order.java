package com.khalej.magsala.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class contact_order  {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;
    @SerializedName("date")
    String date;
    @SerializedName("phone")
    String phone;
    @SerializedName("address")
    String address;
    @SerializedName("details")
    String details;
    @SerializedName("price")
    int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
