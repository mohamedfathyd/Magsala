package com.khalej.magsala.Model;

import com.google.gson.annotations.SerializedName;

public class user_content {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;

    @SerializedName("phone")
    String phone;
    @SerializedName("password")
    String password;
    @SerializedName("Last_login")
    String last_login;
    @SerializedName("lat")
    String lat;
    @SerializedName("log")
    String log;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
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


}
