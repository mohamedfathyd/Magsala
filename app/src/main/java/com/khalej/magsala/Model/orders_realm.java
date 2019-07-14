package com.khalej.magsala.Model;

import com.google.gson.annotations.SerializedName;

import io.realm.Realm;
import io.realm.RealmObject;

public class orders_realm  extends RealmObject {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;
    @SerializedName("details")
    String details;

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
